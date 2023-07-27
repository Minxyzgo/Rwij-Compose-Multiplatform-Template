group "cn.minxyzgo.rwpp"
version "1.0-SNAPSHOT"

plugins {
    kotlin("multiplatform") version "1.8.20" apply false
    kotlin("android") apply false version "1.8.20" apply false
    id("com.android.application") apply false version "7.4.2"
    id("com.android.library") apply false version "7.4.2"
    id("org.jetbrains.compose") version "1.4.1" apply false
}

allprojects {
    buildscript {
        repositories {
            google()
            mavenCentral()
            maven("https://jitpack.io")
            gradlePluginPortal()
        }
    }
}
