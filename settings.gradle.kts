rootProject.name = "kotlin-advent-of-code-2023"

pluginManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/amper/amper")
    }
}

plugins {
    id("org.jetbrains.amper.settings.plugin").version("0.1.1")
}
