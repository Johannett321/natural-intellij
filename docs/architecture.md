# Architecture

How the plugin is put together, and where to look when you want to change something.

## The pipeline

Natural source becomes a navigable PSI tree in four stages:

```
   .NSP file
      │
      ▼
  ┌─────────────────┐   Natural.flex  ──generates──▶  _NaturalLexer.java
  │  Lexer          │   Character stream → tokens (KW_READ, IDENTIFIER, NUMBER, …)
  └─────────────────┘
      │  token stream
      ▼
  ┌─────────────────┐   Natural.bnf   ──generates──▶  NaturalParser.java + 150+ PSI classes
  │  Parser         │   Tokens → AST, following the BNF grammar rules
  └─────────────────┘
      │  AST
      ▼
  ┌─────────────────┐   Mixin classes in psi/
  │  PSI model      │   AST + names, references and resolution behaviour
  └─────────────────┘
      │
      ▼
  ┌─────────────────┐
  │  IDE features   │   completion, navigation, folding, structure, …
  └─────────────────┘
```

Two source files define the language. Everything under `src/main/gen/` is generated from them
and is never edited by hand.

| Source | Lines | Generates |
|---|---:|---|
| `src/main/flex/Natural.flex` | ~750 | `_NaturalLexer` |
| `src/main/bnf/Natural.bnf` | ~1370 | `NaturalParser` and the PSI class hierarchy |

## The lexer

Natural cannot be tokenised with a single flat rule set, because the same characters mean
different things in different regions of a file. `Natural.flex` therefore uses several lexer
states:

- **`YYINITIAL` / `YYINITIAL_NL`** — normal code. Tracks whether we are at the start of a line,
  because a leading `*` is a comment while an inner `*` is multiplication.
- **`DEFINE_DATA`** — inside a `DEFINE DATA` block. Level numbers (`1`, `2`, `3`) are structural
  here, and type specifications like `(A20)`, `(I4)`, `(N7.2)`, `(P9.2)` are lexed as data types
  rather than as parenthesised expressions.
- **Inline data / map states** — regions where content is essentially literal payload and must
  not be tokenised as code.

The practical consequence: a token's meaning depends on the state it was produced in. `N2` is a
data type inside `DEFINE DATA` and an ordinary identifier outside it. When adding rules, put them
in the right state, and check whether the same text needs a rule in more than one.

## The parser and PSI

Grammar-Kit turns `Natural.bnf` into a recursive-descent parser plus one PSI interface and
implementation per rule. Rules that need behaviour beyond "hold child nodes" get a **mixin**: a
hand-written class named in the BNF that the generated implementation extends.

| PSI element | Mixin | Responsibility |
|---|---|---|
| `NaturalFile` | `psi/NaturalFile.kt` | Root node; file-level queries |
| `variableDecl` | `psi/NaturalVariableDeclMixin.kt` | A declaration: exposes its name, acts as a rename target |
| `variableRef` | `psi/NaturalVariableRefMixin.kt` | A usage: resolves to its declaration |
| `subroutineName` | `psi/NaturalSubroutineNameMixin.kt` | `DEFINE SUBROUTINE` name |
| `subroutineRef` | `psi/NaturalSubroutineRefMixin.kt` | `PERFORM` target; resolves to the declaration |

Shared helpers live in `psi/NaturalPsiImplUtil.kt` — the BNF `methods=` attribute points at
functions here, so a single utility can serve many rule types. `psi/references/` holds the
generic named-element plumbing (`SimpleNamedElement` and friends) that rename and find-usages
build on.

The rule is: **never modify generated PSI**. Add behaviour through a mixin or a util function.

## Resolution across files

Natural objects live in libraries as flat 8-character names, so resolving a `CALLNAT` or a
`LOCAL USING` means searching the project rather than following a path. Two file-based indexes
make that fast:

| Index | Maps |
|---|---|
| `index/NaturalCallnatIndex` | subprogram name → call sites |
| `index/NaturalDataAreaUsageIndex` | data area name → objects that use it |

`steplib/NaturalSteplibService` reads the NaturalONE `.natural` project file to learn the library
**steplib chain** — the ordered list of libraries a library inherits objects from — so resolution
follows the same precedence the Natural runtime does. `reference/NaturalDataAreaUtils` handles the
on-disk layout, where an object's type is encoded in its containing folder
(`MYLIB/Programs/FOO.NSP`, `MYLIB/Local/BAR.NSL`).

## Where each feature lives

All of these are registered in `src/main/resources/META-INF/plugin.xml`.

| Feature | Implementation |
|---|---|
| Syntax highlighting | `highlighting/NaturalSyntaxHighlighter.kt`, colours in `NaturalColorSettingsPage` |
| Token sets (keywords etc.) | `lexer/NaturalTokenTypes.kt` |
| Code completion | `completion/NaturalCompletionContributor.kt` + providers in `completion/keywordproviders/` |
| Go to declaration | `reference/NaturalGotoDeclarationHandler.kt` |
| Find usages | `reference/NaturalFindUsagesProvider.kt`, `findusages/` |
| Rename | `reference/NaturalRefactoringSupportProvider.kt` |
| Documentation on hover | `documentation/NaturalDocumentationProvider.kt` |
| Code folding | `folding/NaturalFoldingBuilder.kt` |
| Structure view | `structure/NaturalStructureViewFactory.kt` |
| Formatter | `formatting/NaturalFormattingModelBuilder.kt` |
| Annotator (inspections) | `annotator/NaturalAnnotator.kt` |
| Intentions | `intentions/` (generate variable/subroutine, extract subroutine/subprogram) |
| Map editor | `mapeditor/` — parser, model, canvas, writer for `.NSM` |
| Nicknames | `nickname/` — service, project-view decorator, tab titles, Search Everywhere |
| Run configurations | `run/` — local execution through an embedded terminal |
| File types | `language/filetypes/` — one class per extension |
| Icons | `NaturalIcons.kt`, SVGs in `resources/icons/` |

### Adding a file type

A new extension needs three things: a `NaturalFileType` subclass in `language/filetypes/`, an
icon entry in `NaturalIcons.kt`, and a `<fileType>` registration in `plugin.xml`.

### Making a block foldable

Add the generated PSI class to the `foldableTypes` list in `folding/NaturalFoldingBuilder.kt`.
The class name follows the BNF rule name: `onErrorBlock` → `NaturalOnErrorBlock`.

## The optional NDV module

Everything above is self-contained and has no third-party runtime dependencies beyond flexmark.
The Natural Development Server integration is separate, lives in `src/ndv/`, and is compiled only
when Software AG's proprietary client libraries are available locally. See
[`ndv-integration.md`](ndv-integration.md).

## Tests

| Test | Purpose |
|---|---|
| `NaturalParserDebugTest` | Targeted regression cases — small snippets that previously mis-parsed |
| `NaturalParserBulkTest` | Runs the parser over a whole corpus and groups the failures by message |

The bulk test needs a corpus, and none ships with the project because Natural sources are
customer code. Point it at a library you have access to:

```bash
./gradlew test --tests "*NaturalParserBulkTest" -Dnatural.source.root=/path/to/library
```

Without the property it prints a notice and passes. It is the fastest way to find out whether a
grammar change helped or hurt: run it before and compare the error counts. The bundled
`examples/DEMOLIB` works as a small smoke-test corpus.
