@file:OptIn(LibRequiredApi::class)

import com.github.minxyzgo.rwij.*
import com.github.minxyzgo.rwij.GradlePlugin.Companion.injectRwLib
import com.github.minxyzgo.rwij.ProxyFactory.with
import javassist.ClassMap
import javassist.CtClass
import javassist.bytecode.Descriptor

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
}

group = "cn.minxyzgo.rwpp"
version = "1.0-SNAPSHOT"

kotlin {
    android()
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.ui)
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                api("androidx.appcompat:appcompat:1.6.1")
                api("androidx.core:core-ktx:1.10.1")
                api("androidx.activity:activity-compose:1.7.2")
//                implementation("androidx.work:work-runtime:2.7.1")
//                implementation("androidx.work:work-runtime-ktx:2.7.1")
            }
        }

        val desktopMain by getting {
            dependencies {
                api(compose.preview)
            }
        }
        val desktopTest by getting
    }
}

android {
    compileSdk = 34

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/commonMain/resources")

    defaultConfig {
        targetSdk = 34
        minSdk = 31
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlin {
        jvmToolchain(11)
    }

    namespace = "cn.minxyzgo.rwpp.common"
    buildToolsVersion = "34.0.0"
}

buildscript {
    dependencies {
        classpath("com.github.minxyzgo.rw-injection:com.github.minxyzgo.rwij.gradle.plugin:1.9.4")
    }
}

apply<com.github.minxyzgo.rwij.GradlePlugin>()

injectionMultiplatform {
    enable = true
    com.github.minxyzgo.rwij.Libs.Companion.includes.add(com.github.minxyzgo.rwij.Libs.`android-game-lib`)
    com.github.minxyzgo.rwij.Builder.releaseLibActions[com.github.minxyzgo.rwij.Libs.`android-game-lib`] = { _, fi, _ ->
        if(!fi.exists()) {
            fi.mkdirs()
            fi.createNewFile()
        }
        fi.writeBytes(File(projectDir.absolutePath + "/android-game-lib-template.jar").readBytes())
    }
    android {
        setProxy(com.github.minxyzgo.rwij.Libs.`android-game-lib`, "com.corrodinggames.rts.game.i".with("n"))
        setProxy(com.github.minxyzgo.rwij.Libs.`android-game-lib`, "com.corrodinggames.rts.gameFramework.j.ae".with("X", "d(Ljava/lang/String;Ljava/lang/String;)"))
        action {
            Libs.`android-game-lib`.classTree.defPool["com.corrodinggames.rts.gameFramework.j.ae"].apply {
                //make accessible
                getDeclaredMethod("f", arrayOf(CtClass.intType)).let {
                    it.modifiers = javassist.Modifier.setPublic(it.modifiers)
                }

                declaredMethods.filter { it.name == "a" }.forEach {
                    it.modifiers = javassist.Modifier.setPublic(it.modifiers)
                }

                getDeclaredField("bD").let {
                    it.modifiers = javassist.Modifier.setPublic(it.modifiers)
                }
            }

            val tag = listOf("anim", "array", "attr", "color", "drawable", "id", "layout", "raw", "string", "style", "styleable", "xml")
            val classMap = ClassMap().apply {
                put("com.corrodinggames.rts.R", "cn.minxyzgo.rwpp.android.R")
                tag.forEach {
                    put("com.corrodinggames.rts.R\$$it", "cn.minxyzgo.rwpp.android.R\$$it")
                }
            }
            com.github.minxyzgo.rwij.Libs.`android-game-lib`.classTree.allClasses.forEach {
                it.replaceClassName(classMap)
            }
        }
    }
    jvm {
        target = "desktopMain"
        action {
            com.github.minxyzgo.rwij.Libs.`game-lib`.classTree.defPool["com.corrodinggames.rts.java.Main"].apply {
                val staticConstructor = this.classInitializer
                staticConstructor.insertAfter("c = \"Rusted Warfare!!!!!\";")
            }
        }
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        mavenLocal()
        maven("https://jitpack.io")
    }
}

dependencies {
    injectRwLib("1.9.4")
    commonMainApi("dev.icerock.moko:resources:0.23.0")
    commonMainApi("dev.icerock.moko:resources-compose:0.23.0") // for compose multiplatform
}
