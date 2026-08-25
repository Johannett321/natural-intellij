# Natural Development Server integration

The optional module that lets the plugin talk to a Natural Development Server (NDV): browsing
libraries, opening remote objects, stow/check, diffing local against remote, and remote debugging.

## Why it is optional

The NDV wire protocol (NATSPOD) is not publicly specified. This plugin therefore does not
implement it directly — it drives Software AG's own **NaturalONE client libraries**, the same jars
the NaturalONE Eclipse product uses:

```
com.softwareag.naturalone.natural.ndvserveraccess_*.jar
com.softwareag.naturalone.natural.auxiliary_*.jar
com.softwareag.natural.tools_*.jar
```

Those libraries are proprietary. They are not in this repository, are not in any published
release, and must not be redistributed. Everything that depends on them is quarantined in
`src/ndv/` and compiled only when you supply them locally.

If you do not have a licensed NaturalONE installation, the plugin still builds and works — you get
the full language support and lose only the server features.

## How the split works

Three pieces, all driven by whether the jars are found.

**1. A separate source root.** `src/ndv/kotlin` holds every class that touches a
`com.softwareag.*` type, plus the classes that depend on those:

```
src/ndv/kotlin/com/appweb/natural/intellij/
├── nds/          NdsClient, NdsServer, NdsServerSettings, NdsServerConfigurable
├── debug/        remote debugger: process, session, stack frames, breakpoints, values
├── compare/      diff a local file against the server version
├── actions/      StowAndCheckActions (stow + check)
└── toolwindow/   NdsToolWindowFactory (the Natural Servers tree)
```

`src/main/` has no reference to any Software AG type — verify with:

```bash
grep -r "com.softwareag" src/main/    # must return nothing
```

**2. A separate plugin descriptor.** `src/ndv/resources/META-INF/natural-ndv.xml` registers the
tool window, the settings page, the debugger and the actions. The core `plugin.xml` pulls it in
with an *optional* XInclude:

```xml
<xi:include href="/META-INF/natural-ndv.xml" xpointer="xpointer(/idea-plugin/*)">
    <xi:fallback/>
</xi:include>
```

In a core-only build the file simply is not in the jar, and `<xi:fallback/>` turns the include
into a no-op. No dangling class references, no load errors.

**3. Conditional Gradle wiring.** `build.gradle.kts` looks for the jars, then adds the source
root, the resource root and the dependencies only if it found them:

```kotlin
val ndvJars: FileCollection = fileTree(ndvLibDir) { include("com.softwareag.…*.jar") }
val ndvEnabled: Boolean = ndvRequested && !ndvJars.isEmpty

if (ndvEnabled) {
    kotlin.sourceSets["main"].kotlin.srcDir("src/ndv/kotlin")
    sourceSets["main"].resources.srcDir("src/ndv/resources")
}
```

It announces which mode it chose, so you are never guessing:

```
Natural: NDV server support ENABLED (3 client jar(s) from …/libs)
Natural: NDV server support DISABLED - building core language support only.
```

## Building with server support

Either point Gradle at a NaturalONE installation:

```bash
./gradlew buildPlugin -PnaturalOneLibDir=/path/to/NaturalOne/Designer/eclipse/plugins
```

or drop the three jars into `libs/` (git-ignored) and build normally. Force a core-only build
with `-PndvEnabled=false`.

ICU4J is pulled from Maven Central rather than vendored; the client libraries need its codepage
converters at runtime.

## Connecting to a server

**Settings → Tools → Natural Servers** manages connections. Each entry holds a name, host, port
(2700 by default), user and logon library. **Passwords are never written to project or plugin
configuration** — they go to the IDE's `PasswordSafe`, keyed by the server's generated id:

```kotlin
CredentialAttributes(generateServiceName("NaturalForIntelliJ NDS", serverId))
```

The **Natural Servers** tool window then shows a `server → library → object` tree; opening a node
downloads the source into the editor.

## Implementation notes

`NdsClient` wraps the vendor libraries and works around several of their sharp edges. These are
documented in the class comment and worth knowing before changing it:

- **The async receive thread can die** on certain server responses. Calls poll a `Future` against
  a deadline and an `asyncFailed` flag rather than blocking forever.
- **Connection state degrades after a unit of work.** A second `listObjects` on the same
  connection can overflow an internal 50-byte buffer, so the client opens a fresh connection per
  session — use the `forLibrary` factory or a short-lived `use { }` block rather than holding one
  open.
- **Bytes are decoded with the JVM default charset.** Object and library names may contain
  non-ASCII characters, so the IDE needs `-Dfile.encoding=ISO-8859-1` in its vmoptions; the client
  additionally decodes names itself where it can.

Compile diagnostics come back as `NdsCompileError` (error number, row, column, short and long
text). `StowAndCheckActions` turns those into editor highlights: a red underline on the offending
line, a gutter icon, and a tooltip carrying the `NATnnnn` message.

## A note on protocol reverse engineering

Understanding the vendor libraries well enough to drive them involved inspecting their bytecode
and observing NaturalONE's network traffic. Those working notes and packet captures are **not**
part of this repository — captures record live sessions, including hostnames, user identifiers and
payloads, and `.gitignore` excludes `wiresharkdumps/`, `*.pcap` and `*.pcapng` so they cannot be
committed by accident.

If you do protocol work of your own, keep the captures out of the repo.
