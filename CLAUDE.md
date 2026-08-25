# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What This Is

An IntelliJ IDEA plugin providing language support for **Software AG's Natural programming
language** (enterprise mainframe language). The plugin supports 10 file types (NSP, NSN, NSC, NSD,
NSG, NSL, NSM, NPD, NSS, NST) and optionally integrates with Natural Development Servers (NDV)
through the vendor's client libraries.

This is a public, open-source repository (Apache-2.0). Two consequences that matter constantly:

- **Never commit proprietary binaries or protocol captures.** `.gitignore` covers `libs/`,
  `wiresharkdumps/`, `*.pcap`, `*.pcapng`.
- **Never commit customer-identifying content** — real library names, program names, business
  identifiers, internal project codes, or local filesystem paths. Use generic placeholders
  (`MYLIB`, `CUSTOMER`, `FIELD-A`) in code, comments, tests and docs.

## Commands

```bash
./gradlew runIde                 # Run plugin in a sandboxed IntelliJ instance
./gradlew runIde -PopenSample    # ...and open examples/DEMOLIB in it
./gradlew test                   # Run all tests
./gradlew buildPlugin            # Build distributable plugin ZIP
./gradlew verifyPlugin           # Verify plugin compatibility with target platform
./gradlew check                  # Run tests with Kover code coverage
```

### Build modes

`src/ndv/` holds the Natural Development Server integration, which compiles against Software AG's
proprietary NaturalONE client libraries. Those are **never committed and never published**. Gradle
includes that source set only when the jars are present in `libs/` (git-ignored) or supplied via
`-PnaturalOneLibDir=...`; pass `-PndvEnabled=false` to force a core-only build. Gradle logs which
mode it chose. Released builds are always core-only.

**`src/main/` must never reference `com.softwareag`.** Verify with
`grep -r com.softwareag src/main/` — it must return nothing, otherwise a default build breaks
for anyone without the proprietary libraries.

Run configurations in `.run/` can also be used directly from IntelliJ.

## Tool Preferences

- Use the **Read tool** to inspect Natural source files (`.NSP`, `.NSN`, `.NSC`, `.NSL`, `.NPD`, etc.) rather than shell commands like `sed`, `cat`, or `head`. The Read tool supports line offsets and limits, making it well-suited for navigating large source files.

## Architecture

### Lexer & Parser Pipeline

The language grammar is defined in two generated-code source files:

- `src/main/flex/Natural.flex` — JFlex lexer with multiple states (DEFINE DATA section, inline data, normal code). Generates `_NaturalLexer`.
- `src/main/bnf/Natural.bnf` — Grammar-Kit BNF grammar. Generates the parser and all PSI element classes.

**Do not edit generated files** under `src/main/gen/`. Edit the `.flex` or `.bnf` source files instead, then rebuild.

After editing `.bnf`, run **Generate Parser Code** (right-click the file in IntelliJ).
After editing `.flex`, run **Run JFlex Generator** (right-click the file in IntelliJ).

### PSI Model

The PSI tree built from parsed Natural code has these key node types:
- `NaturalFile` — root PSI node
- `NaturalVariableDecl` / `NaturalVariableRef` — variable definitions and usages (via mixins in `psi/`)
- `NaturalSubroutineNameMixin` / `NaturalSubroutineRefMixin` — subroutine declarations and call sites
- `SimpleNamedElement` — base interface for all named elements

PSI implementation utilities live in `NaturalPsiImplUtil.kt`. Custom behavior is injected via mixin classes rather than modifying generated PSI directly.

### Documentation

Longer-form docs live in `docs/` and are the canonical reference:

- `docs/architecture.md` — pipeline, PSI model, indexes, feature map
- `docs/grammar.md` — grammar editing, ordering/`pin`, lookahead guards, pitfalls
- `docs/ndv-integration.md` — the optional server module and how it is isolated

### IDE Feature Implementations

| Feature | Key Class |
|---|---|
| Syntax highlighting | `highlighting/NaturalSyntaxHighlighter.kt` |
| Code completion | `completion/NaturalCompletionContributor.kt` + 4 providers in `keywordproviders/` |
| Ctrl+Click navigation | `reference/NaturalGotoDeclarationHandler.kt` |
| Find usages | `reference/NaturalFindUsagesProvider.kt` |
| Rename refactoring | `reference/NaturalRefactoringSupportProvider.kt` |
| Structure view | `structure/NaturalStructureViewFactory.kt` |
| Code folding | `folding/NaturalFoldingBuilder.kt` |
| Hover docs | `documentation/NaturalDocumentationProvider.kt` |

All extensions and actions are registered in `src/main/resources/META-INF/plugin.xml`.

### NDS (Natural Development Server) Integration

Lives in `src/ndv/` (optional source set — see Build modes above), not `src/main/`.

`nds/NdsClient.kt` drives Software AG's NaturalONE client libraries to talk to a Natural
Development Server. The tool window renders a browsable tree of servers → libraries → objects.
Connection settings persist via `NdsServerSettings.kt` (application service); **passwords go to
the IDE `PasswordSafe`**, never to configuration files. Extensions register through
`src/ndv/resources/META-INF/natural-ndv.xml`, pulled in by an optional `<xi:include>` with an
`<xi:fallback/>` so core-only builds have no dangling references.

### File Types

All 10 Natural file types extend `NaturalFileType` base class and live in `language/filetypes/`. Adding a new file type requires: a new class, an icon in `resources/icons/`, and registration in `plugin.xml`.

## Key Configuration

- `gradle.properties` — plugin version (`pluginVersion`), IntelliJ platform version (`platformVersion`), Java toolchain version
- `gradle/libs.versions.toml` — dependency version catalog
- Plugin ID: `com.appweb.natural.intellij`
- Targets IntelliJ Platform `2025.2.6.1`, Java 21

---

## Natural Language Reference

This section documents Natural language constructs as they are understood and parsed by this plugin. The official reference is at https://documentation.softwareag.com/natural/nat921unx/webhelp/natux-webhelp/

### General Syntax Conventions

- Natural is **case-insensitive** for keywords (WRITE, write, Write are all valid)
- Variables use dotted qualification: `GROUP.FIELD` (e.g. `REC40.POSTNR-UTL`)
- Line comments start with `*`
- Inline comments use `/*`
- Statements are generally one per line; no statement terminator
- Block constructs always have a matching `END-xxx` keyword

### Data Types and Variables

Natural variables are declared in a `DEFINE DATA` section at the top of the program. Variable names can contain hyphens and dots:

```natural
DEFINE DATA LOCAL
  1 REC40
    2 POSTNR-UTL   (A5)
    2 POSTSTED     (A30)
  1 #COUNTER       (I4)
  1 STED (A16)
END-DEFINE
```

Level numbers (1, 2, 3…) define group hierarchy. Level 1 is a top-level group; higher levels are nested fields. Leaf fields have a data type in parentheses, e.g. `(A20)` = 20-char alphanumeric, `(I4)` = 4-byte integer, `(N7.2)` = numeric with 2 decimal places.

### Masks

Conditions support `MASK(...)` for pattern matching:

```natural
IF FIELD = MASK(nnnn' ')   * 4 digits followed by a space
```

Mask characters: `n`/`9` = digit, `A`/`X` = alphanumeric, `'...'` = literal characters.

### Edit Masks

Edit masks appear in parentheses after a variable reference in session-parameter context, e.g.:

```natural
MOVE EDITED REC30.BELØP (EM=ZZZ:ZZZ:ZZ9.99) TO A20
```

Mask characters: `Z` = suppress leading zero, `9` = always show digit, `.` = decimal point, `,` = thousands separator, `:` = grouping separator (colon variant).

### Condition Operators

```natural
=  EQ  EQUAL TO
^=  NE  NOT EQUAL TO
<  LT  LESS THAN
>  GT  GREATER THAN
<= LE  LESS EQUAL
>= GE  GREATER EQUAL
```

Compound conditions use `AND` / `OR` / `NOT`. Shorthand RHS repetition:

```natural
IF A = '11' OR = '19' OR = '31'
```

---

## Grammar Patterns and Pitfalls

This section documents recurring patterns and known pitfalls when editing `Natural.bnf`.

### Adding a New Keyword

Three files must always be updated together:

1. **`Natural.bnf`** — add to the `tokens` block:
   ```
   KW_FOO = "FOO"
   ```

2. **`Natural.flex`** — add a lexer rule in the appropriate state (usually the general keyword state):
   ```
   "FOO" { return KW_FOO; }
   ```

3. **`NaturalTokenTypes.kt`** — add a `@JvmField val` declaration and add the token to the keyword token set (the `TokenSet` used for syntax highlighting):
   ```kotlin
   @JvmField val KW_FOO: IElementType = NaturalTypes.KW_FOO
   // and in the keyword TokenSet:
   KW_FOO,
   ```

### Alternative Ordering and `pin`

Grammar-Kit tries alternatives **in order** and commits (pins) to the current alternative after `pin=N` tokens are consumed. This means **more specific rules must come before general ones** when they share a common prefix.

Key example: `WRITE WORK FILE` vs plain `WRITE`. The private `writeStatement` at the top of the `statement` rule has `pin=1` — as soon as it sees `KW_WRITE` it commits. Therefore `writeWorkFileStatement` must be guarded with a negative lookahead on the private rule:

```bnf
private writeStatement ::= ((KW_WRITE !KW_WORK) | KW_DISPLAY | KW_PRINT) writeArg* { pin=1 }
```

And within `simpleStatements`, `writeWorkFileStatement` must be listed **before** `writeStatement`.

### Preventing Over-Consumption with `!statement`

When a rule allows repeating expressions (`expression+` or `expression*`), use the `!statement` guard to prevent consuming tokens that belong to the next statement:

```bnf
private writeArg  ::= !statement expression
private moveTarget ::= !statement expression
private resetArg  ::= !statement expression
```

Without this guard, `expression+` will greedily consume the first token of the next statement.

### Special Content Inside Parentheses

Some Natural constructs have non-expression content inside parentheses (mask definitions, edit masks). These use a dedicated `*Atom` rule rather than `expression`:

- `editMaskAtom` — used inside session parameter groups `(EM=..., AL=...)`. Allows `IDENTIFIER | NUMBER | STRING_LITERAL | DOT | COLON | PLUS | MINUS | STAR | SLASH | COMMA`.
- `maskAtom` — used inside `MASK(...)` condition expressions. Allows adjacent identifier and string literal tokens without separators (e.g. `nnnn' '`).

### Making a Block Collapsible

Add the generated PSI class to the `foldableTypes` list in `NaturalFoldingBuilder.kt`. The class name is derived from the BNF rule name: `onErrorBlock` → `NaturalOnErrorBlock`.

### `variableName` vs `expression`

`variableName` (used in `FOR` loop variable and `variableDecl`) matches a plain or dotted identifier but not a full expression. If Natural allows a dotted group field in a position, use `IDENTIFIER (DOT IDENTIFIER)*` rather than just `IDENTIFIER`.
