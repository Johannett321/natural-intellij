# Contributing

Thanks for considering it. This plugin exists because Natural developers deserve better tooling,
and the fastest way to improve it is with real-world input.

## The most useful contribution: parse failures

Natural is a large language with a long history and real dialect variation between platforms and
versions. The grammar here covers a lot, but it will not cover everything. **If the plugin fails
to parse code that your Natural compiler accepts, that is a bug worth reporting.**

When you file one, please include:

1. **A minimal snippet that reproduces it** — ideally a complete, tiny object that still shows the
   error. Reduce it until removing anything more makes the error disappear.
2. **What the compiler does** with that snippet. "This compiles cleanly on our server" is the key
   fact; it distinguishes a grammar gap from genuinely broken source.
3. **Object type and platform** — `.NSP` vs `.NSN` vs `.NSC` matters, and so does mainframe vs
   Linux vs Windows Natural, since the accepted syntax differs.
4. **The error the plugin shows**, with line and column.

> **Please do not paste proprietary source.** Rewrite the snippet with generic names — `CUSTOMER`,
> `#COUNTER`, `FIELD-A` — before posting. The grammar does not care what your fields are called,
> and neither does anyone reading the issue. Reports with real business identifiers, internal
> project codes or customer data will be edited or closed.

Copycode (`.NSC`) is a known special case: fragments meant to be `INCLUDE`d inside a parent
`FIND`, `READ` or `DECIDE` block are not standalone-valid, and errors at the start of such a file
are expected rather than bugs.

## Development setup

**Requires JDK 21.**

```bash
git clone https://github.com/Johannett321/natural-intellij.git
cd natural-intellij
./gradlew runIde -PopenSample
```

That launches a sandboxed IDE with the plugin loaded and the `examples/DEMOLIB` library open, so
there is Natural code on screen immediately.

Useful tasks:

```bash
./gradlew test                   # test suite
./gradlew buildPlugin            # distributable ZIP
./gradlew verifyPlugin           # platform compatibility check
./gradlew check                  # tests + Kover coverage
./gradlew generateLexer generateParser   # regenerate from .flex / .bnf
```

Run configurations for these are in [`.run/`](.run) and work directly from IntelliJ.

Server features are off by default because they need proprietary libraries — see
[`docs/ndv-integration.md`](docs/ndv-integration.md) if you have a NaturalONE licence and want to
work on them.

## Before you open a pull request

**Read [`docs/architecture.md`](docs/architecture.md)** for the layout, and
**[`docs/grammar.md`](docs/grammar.md)** if you are touching the language definition — it
documents the ordering and lookahead rules that cause most grammar regressions.

A few hard rules:

- **Never edit anything under `src/main/gen/`.** It is regenerated from `Natural.flex` and
  `Natural.bnf` on every build; your changes will vanish. Edit the sources instead.
- **Adding a keyword touches three files** — `Natural.bnf`, `Natural.flex` and
  `lexer/NaturalTokenTypes.kt`. Miss the third and it will not highlight.
- **Never commit anything to `src/main/` that references `com.softwareag`.** That code belongs in
  `src/ndv/`. CI enforces this.
- **Never commit the Software AG jars, or protocol captures.** `.gitignore` covers `libs/`,
  `wiresharkdumps/`, `*.pcap` and `*.pcapng`; please keep it that way.

### Testing a grammar change

Add a targeted regression case to `NaturalParserDebugTest` — a small snippet with generic names,
asserting it parses without errors. Then, if you have access to a body of Natural source, run the
bulk parser over it **before and after** your change and compare the error counts:

```bash
./gradlew test --tests "*NaturalParserBulkTest" -Dnatural.source.root=/path/to/library
```

This is the only reliable way to catch a fix that breaks something else — and grammar changes do
that often, because alternative ordering has non-local effects. Without the property the test
prints a notice and passes, so CI stays green.

### Style

Match the surrounding code: standard Kotlin conventions, four-space indent, explicit types on
public API. Comments should explain *why* — the platform APIs are obscure enough that a note about
which sharp edge you are avoiding is genuinely useful, while a comment restating the code is not.

## Reporting other bugs and requesting features

Use the issue templates. For a bug, the IDE version, plugin version and a reproduction path are
what make it actionable. For a feature, describing the Natural workflow you are trying to
accomplish helps more than proposing a specific UI — there is often an IntelliJ idiom that fits
better than the obvious design.

## Licence

Contributions are accepted under the [Apache License 2.0](LICENSE), the licence of the project.
