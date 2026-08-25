# Changelog

## Unreleased

## 1.0.0

First public release.

### Added

- **Language support** for ten Natural object types: `.NSP`, `.NSN`, `.NSS`, `.NSC`, `.NSD`,
  `.NSG`, `.NSL`, `.NPD`, `.NSM`, `.NST`.
- **Lexer and parser** built from a JFlex lexer and a Grammar-Kit BNF grammar, producing a full
  PSI model rather than regex-based approximations.
- **Syntax highlighting** with a configurable colour scheme.
- **Code completion** for statements, `DEFINE DATA` clauses, session parameters, system variables
  and in-scope variables.
- **Navigation**: go to declaration for variables, subroutines, `CALLNAT` targets and data areas,
  resolved across files using the library steplib chain.
- **Find usages** and **rename refactoring** for variables and subroutines.
- **Hover documentation** and a NaturalDoc panel for object header comments.
- **Code folding** for block constructs and the `DEFINE DATA` section.
- **Structure view** and a **dependency view** showing callers and callees.
- **Intentions**: generate a missing variable or subroutine; extract a selection into a subroutine
  or subprogram.
- **Map editor** for `.NSM` screen maps.
- **Nicknames** — readable aliases for cryptic object names, shown in the project tree, editor
  tabs and Search Everywhere.
- **Run configurations** for executing Natural objects through a local `natural` executable.
- **Optional Natural Development Server integration**: library and object browsing, opening
  remote sources, stow and check with inline compile errors, diffing local against server, and
  remote debugging with breakpoints. Requires locally supplied NaturalONE client libraries —
  see `docs/ndv-integration.md`.
- **Example library** (`examples/DEMOLIB`) exercising most of the supported grammar.
