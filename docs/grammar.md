# Working on the grammar

Practical notes for editing `Natural.flex` and `Natural.bnf`, including the mistakes that are
easy to make and hard to spot.

## Natural syntax, briefly

Things that matter when writing grammar rules:

- **Case-insensitive keywords.** `WRITE`, `write` and `Write` are all the same token.
- **No statement terminator.** Statements are separated by position, not punctuation, which is
  why lookahead guards matter so much (see below).
- **Hyphens and dots in identifiers.** `POSTNR-UTL` is one identifier; `REC40.POSTNR-UTL` is a
  qualified reference to a group field.
- **Block constructs always close** with a matching `END-xxx`.
- **Comments**: a `*` in column 1 starts a line comment, `/*` starts a trailing comment, and
  `/**` is a doc comment.
- **Level numbers** structure `DEFINE DATA`: level 1 is a top-level group, higher levels nest.

```natural
DEFINE DATA LOCAL
  1 REC40
    2 POSTNR-UTL   (A5)
    2 POSTSTED     (A30)
  1 #COUNTER       (I4)
END-DEFINE
```

Leaf fields carry a type: `(A20)` alphanumeric, `(I4)` integer, `(N7.2)` numeric with decimals,
`(P9.2)` packed, `(L)` logical, `(D)` date.

### Masks and edit masks

Two different constructs, both living inside parentheses, both needing their own rule because
their contents are not expressions.

`MASK(...)` does pattern matching in conditions — `n`/`9` is a digit, `A`/`X` alphanumeric, and
quoted text is literal:

```natural
IF FIELD = MASK(nnnn' ')      /* four digits then a space
```

Edit masks format output, appearing as a session parameter after a reference — `Z` suppresses a
leading zero, `9` always shows a digit, `:` is a grouping separator:

```natural
MOVE EDITED REC30.AMOUNT (EM=ZZZ:ZZZ:ZZ9.99) TO #TEXT
```

### Condition operators

| Symbol | Word forms |
|---|---|
| `=` | `EQ`, `EQUAL TO` |
| `^=` | `NE`, `NOT EQUAL TO` |
| `<` | `LT`, `LESS THAN` |
| `>` | `GT`, `GREATER THAN` |
| `<=` | `LE`, `LESS EQUAL` |
| `>=` | `GE`, `GREATER EQUAL` |

Compound conditions use `AND`, `OR`, `NOT`. The right-hand side may be repeated in shorthand,
with the field and operator implied:

```natural
IF A = '11' OR = '19' OR = '31'
```

---

## Adding a keyword

Three files must change together, or the build breaks in a confusing way.

**1. `Natural.bnf`** — declare the token:

```
KW_FOO = "FOO"
```

**2. `Natural.flex`** — add a lexer rule in the appropriate state:

```
"FOO" { return KW_FOO; }
```

**3. `lexer/NaturalTokenTypes.kt`** — expose it and add it to the keyword `TokenSet`, or it will
not be highlighted as a keyword:

```kotlin
@JvmField val KW_FOO: IElementType = NaturalTypes.KW_FOO
// ...and in the keyword TokenSet:
KW_FOO,
```

Then regenerate: `./gradlew generateLexer generateParser`.

## Alternative ordering and `pin`

Grammar-Kit tries alternatives **in the order written** and commits — *pins* — to the current
alternative once `pin=N` tokens have matched. After pinning, it will not back out and try a later
alternative; it reports an error instead. So **more specific rules must precede more general ones
that share a prefix.**

The canonical example is `WRITE WORK FILE` against plain `WRITE`. The private `writeStatement`
rule has `pin=1`, so the moment it sees `KW_WRITE` it is committed — and `WRITE WORK FILE 1 …`
would parse as a `WRITE` whose first argument is the identifier `WORK`. Two things prevent that:
a negative lookahead on the private rule, and ordering the specific rule first.

```bnf
private writeStatement ::= ((KW_WRITE !KW_WORK) | KW_DISPLAY | KW_PRINT) writeArg* { pin=1 }
```

```bnf
// in simpleStatements: writeWorkFileStatement MUST come before writeStatement
private simpleStatements ::= … | writeWorkFileStatement | writeStatement | …
```

If a statement suddenly stops parsing after you add a new one, suspect ordering first.

## Guarding repetition with `!statement`

Because Natural has no statement terminator, any rule ending in `expression+` or `expression*`
will happily eat the first token of the *next* statement. The fix is a negative lookahead on the
`statement` rule:

```bnf
private writeArg   ::= !statement expression
private moveTarget ::= !statement expression
private resetArg   ::= !statement expression
```

Read this as "another expression, but only if what follows is not the start of a new statement".
Any new rule with unbounded repetition of expressions needs the same guard. The symptom of a
missing one is an error on the line *after* the real problem, often with a column past the end of
the line.

## Non-expression content in parentheses

Some parenthesised content is not an expression and needs a dedicated atom rule:

- **`editMaskAtom`** — inside session parameter groups such as `(EM=…, AL=…, IS=…)`. Accepts
  `IDENTIFIER`, `NUMBER`, `STRING_LITERAL`, `DOT`, `COLON`, `PLUS`, `MINUS`, `STAR`, `SLASH`,
  `COMMA`.
- **`maskAtom`** — inside `MASK(...)`. Accepts identifier and string tokens *adjacent with no
  separator*, which is what makes `MASK(nnnn' ')` parse.

Reaching for `expression` in these positions is the usual mistake; it fails on the separators.

## `variableName` versus `expression`

`variableName` matches a plain or dotted identifier and nothing more. It is used for the `FOR`
loop variable and in `variableDecl`. If Natural allows a qualified group field in some position,
write `IDENTIFIER (DOT IDENTIFIER)*` rather than a bare `IDENTIFIER` — otherwise
`REC40.POSTNR-UTL` parses as `REC40` followed by an unexpected `.`.

Use `expression` only where a full expression really is allowed; it is greedy and will mask
ordering problems elsewhere.

## Workflow for a grammar change

1. Write a failing case in `NaturalParserDebugTest` — the smallest snippet that reproduces it.
2. Change the `.bnf` or `.flex` source.
3. `./gradlew test --tests "*NaturalParserDebugTest"` until it passes.
4. Run the bulk test over a corpus **before and after** and compare error counts, to catch
   regressions the targeted test cannot see:

   ```bash
   ./gradlew test --tests "*NaturalParserBulkTest" -Dnatural.source.root=/path/to/library
   ```

5. If you touched `Natural.bnf` and added or renamed a rule, remember that PSI class names are
   derived from rule names — a rename may need updating in mixins, `foldableTypes`, or
   `plugin.xml`.

## Reading parser errors

| Symptom | Usual cause |
|---|---|
| Error on the line *after* the real problem | Missing `!statement` guard — the previous statement over-consumed |
| Column number past the end of the line | Same; the parser ran off the end looking for more arguments |
| A newly added statement never matches | Alternative ordering — a more general rule with `pin` matched first |
| Error at the very start of a copycode file | Expected: `.NSC` fragments are designed to be `INCLUDE`d inside a parent block and are not standalone-valid |
| An identifier reported as a data type, or vice versa | Wrong lexer state — check whether the rule belongs in `DEFINE_DATA` |
