import org.jetbrains.changelog.Changelog
import org.jetbrains.changelog.markdownToHTML
import org.jetbrains.intellij.platform.gradle.TestFrameworkType

plugins {
    id("java") // Java support
    alias(libs.plugins.kotlin) // Kotlin support
    alias(libs.plugins.intelliJPlatform) // IntelliJ Platform Gradle Plugin
    alias(libs.plugins.changelog) // Gradle Changelog Plugin
    alias(libs.plugins.qodana) // Gradle Qodana Plugin
    alias(libs.plugins.kover) // Gradle Kover Plugin
    alias(libs.plugins.grammarKit) // Grammar-Kit (JFlex + BNF parser generation
}

group = providers.gradleProperty("pluginGroup").get()
version = providers.gradleProperty("pluginVersion").get()

// Set the JVM language level used to build the project.
kotlin {
    jvmToolchain(21)
}

sourceSets.main {
    java.srcDirs("src/main/gen")
}

// --- Optional Natural Development Server (NDV/SPoD) support -----------------------------------
//
// Server browsing, stow/check and the remote debugger talk to the server through Software AG's
// NaturalONE client libraries. Those are proprietary and are NOT redistributed with this project,
// so the NDV module is compiled only when they are available locally.
//
// To build with server support, either drop the jars into ./libs (git-ignored) or point Gradle at
// an existing NaturalONE installation:
//
//     ./gradlew buildPlugin -PnaturalOneLibDir=/path/to/NaturalOne/Designer/eclipse/plugins
//
// Set -PndvEnabled=false to force a core-only build even when the jars are present.
// See README.md -> "Building with NDV server support".
val ndvLibDir: File = providers.gradleProperty("naturalOneLibDir")
    .map { file(it) }
    .getOrElse(layout.projectDirectory.dir("libs").asFile)

val ndvJars: FileCollection = fileTree(ndvLibDir) {
    include("com.softwareag.naturalone.natural.ndvserveraccess*.jar")
    include("com.softwareag.naturalone.natural.auxiliary*.jar")
    include("com.softwareag.natural.tools*.jar")
}

val ndvRequested: Boolean = providers.gradleProperty("ndvEnabled").map { it.toBoolean() }.getOrElse(true)
val ndvEnabled: Boolean = ndvRequested && !ndvJars.isEmpty

if (ndvEnabled) {
    kotlin.sourceSets["main"].kotlin.srcDir("src/ndv/kotlin")
    sourceSets["main"].resources.srcDir("src/ndv/resources")
    logger.lifecycle("Natural: NDV server support ENABLED (${ndvJars.files.size} client jar(s) from $ndvLibDir)")
} else {
    logger.lifecycle("Natural: NDV server support DISABLED - building core language support only.")
}

// Configure project's dependencies
repositories {
    mavenCentral()

    // IntelliJ Platform Gradle Plugin Repositories Extension - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-repositories-extension.html
    intellijPlatform {
        defaultRepositories()
    }
}

// Dependencies are managed with Gradle version catalog - read more: https://docs.gradle.org/current/userguide/version_catalogs.html
dependencies {
    implementation(libs.flexmark)
    implementation(libs.flexmark.ext.tables)

    if (ndvEnabled) {
        // Proprietary Software AG NaturalONE client libraries, resolved from a local installation.
        // Never checked in and never published - see the note on sourceSets above.
        implementation(ndvJars)
        // ICU4J supplies the mainframe codepage converters the NDV client needs at runtime.
        implementation(libs.icu4j)
        implementation(libs.icu4j.charset)
    }

    testImplementation(libs.junit)
    testImplementation(libs.opentest4j)

    // IntelliJ Platform Gradle Plugin Dependencies Extension - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-dependencies-extension.html
    intellijPlatform {
        intellijIdea(providers.gradleProperty("platformVersion"))

        // Plugin Dependencies. Uses `platformBundledPlugins` property from the gradle.properties file for bundled IntelliJ Platform plugins.
        bundledPlugins(providers.gradleProperty("platformBundledPlugins").map { it.split(',') })

        // Plugin Dependencies. Uses `platformPlugins` property from the gradle.properties file for plugin from JetBrains Marketplace.
        plugins(providers.gradleProperty("platformPlugins").map { it.split(',') })

        // Module Dependencies. Uses `platformBundledModules` property from the gradle.properties file for bundled IntelliJ Platform modules.
        bundledModules(providers.gradleProperty("platformBundledModules").map { it.split(',') })

        testFramework(TestFrameworkType.Platform)
    }
}

// Configure IntelliJ Platform Gradle Plugin - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-extension.html
intellijPlatform {
    pluginConfiguration {
        name = providers.gradleProperty("pluginName")
        version = providers.gradleProperty("pluginVersion")

        // Extract the <!-- Plugin description --> section from README.md and provide for the plugin's manifest
        description = providers.fileContents(layout.projectDirectory.file("README.md")).asText.map {
            val start = "<!-- Plugin description -->"
            val end = "<!-- Plugin description end -->"

            with(it.lines()) {
                if (!containsAll(listOf(start, end))) {
                    throw GradleException("Plugin description section not found in README.md:\n$start ... $end")
                }
                subList(indexOf(start) + 1, indexOf(end)).joinToString("\n").let(::markdownToHTML)
            }
        }

        val changelog = project.changelog // local variable for configuration cache compatibility
        // Get the latest available change notes from the changelog file
        changeNotes = providers.gradleProperty("pluginVersion").map { pluginVersion ->
            with(changelog) {
                renderItem(
                    (getOrNull(pluginVersion) ?: getUnreleased())
                        .withHeader(false)
                        .withEmptySections(false),
                    Changelog.OutputType.HTML,
                )
            }
        }

        ideaVersion {
            sinceBuild = providers.gradleProperty("pluginSinceBuild")
        }
    }

    signing {
        certificateChain = providers.environmentVariable("CERTIFICATE_CHAIN")
        privateKey = providers.environmentVariable("PRIVATE_KEY")
        password = providers.environmentVariable("PRIVATE_KEY_PASSWORD")
    }

    publishing {
        token = providers.environmentVariable("PUBLISH_TOKEN")
        // The pluginVersion is based on the SemVer (https://semver.org) and supports pre-release labels, like 2.1.7-alpha.3
        // Specify pre-release label to publish the plugin in a custom Release Channel automatically. Read more:
        // https://plugins.jetbrains.com/docs/intellij/publishing-plugin.html#specifying-a-release-channel
        channels = providers.gradleProperty("pluginVersion").map { listOf(it.substringAfter('-', "").substringBefore('.').ifEmpty { "default" }) }
    }

    pluginVerification {
        ides {
            recommended()
        }
    }
}

// Configure Gradle Changelog Plugin - read more: https://github.com/JetBrains/gradle-changelog-plugin
changelog {
    groups.empty()
    repositoryUrl = providers.gradleProperty("pluginRepositoryUrl")
    versionPrefix = ""
}

// Configure Gradle Kover Plugin - read more: https://kotlin.github.io/kotlinx-kover/gradle-plugin/#configuration-details
kover {
    reports {
        total {
            xml {
                onCheck = true
            }
        }
    }
}

tasks {
    wrapper {
        gradleVersion = providers.gradleProperty("gradleVersion").get()
    }

    publishPlugin {
        dependsOn(patchChangelog)
    }

    runIde {
        // `./gradlew runIde -PopenSample` opens the bundled example library in the sandbox IDE,
        // so there is Natural code on screen straight away.
        if (providers.gradleProperty("openSample").isPresent) {
            args(layout.projectDirectory.dir("examples/DEMOLIB").asFile.absolutePath)
        }
    }

    test {
        jvmArgs("-Djava.awt.headless=true")

        // Forward the optional bulk-parser corpus location into the test JVM, so
        // `./gradlew test -Dnatural.source.root=...` works as documented.
        System.getProperty("natural.source.root")?.let {
            systemProperty("natural.source.root", it)
        }
    }


    // Grammar-Kit: Generate lexer from Natural.flex
    generateLexer {
        sourceFile.set(layout.projectDirectory.file("src/main/flex/Natural.flex"))
        targetOutputDir.set(layout.projectDirectory.dir("src/main/gen/com/appweb/natural/intellij/lexer"))
        purgeOldFiles.set(true)
    }

    // Grammar-kit: generate parser + PSI from Natural.bnf
    generateParser {
        sourceFile.set(layout.projectDirectory.file("src/main/bnf/Natural.bnf"))
        targetRootOutputDir.set(layout.projectDirectory.dir("src/main/gen"))
        pathToParser.set("com/appweb/natural/intellij/parser/NaturalParser.java")
        pathToPsiRoot.set("com/appweb/natural/intellij/psi")
        purgeOldFiles.set(true)
    }

    compileKotlin {
        dependsOn(generateLexer, generateParser)
    }

    compileJava {
        dependsOn(generateLexer, generateParser)
    }
}

intellijPlatformTesting {
    runIde {
        register("runIdeForUiTests") {
            task {
                jvmArgumentProviders += CommandLineArgumentProvider {
                    listOf(
                        "-Drobot-server.port=8082",
                        "-Dide.mac.message.dialogs.as.sheets=false",
                        "-Djb.privacy.policy.text=<!--999.999-->",
                        "-Djb.consents.confirmation.enabled=false",
                    )
                }
            }

            plugins {
                robotServerPlugin()
            }
        }
    }
}
