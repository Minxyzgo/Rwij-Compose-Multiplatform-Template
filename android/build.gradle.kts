plugins {
    id("org.jetbrains.compose")
    id("com.android.application")
    kotlin("android")
}

group = "cn.minxyzgo.rwpp"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    mavenLocal()
    maven("https://jitpack.io")
}

dependencies {
    api(project(":common"))
    api("androidx.appcompat:appcompat:1.6.1")
    api("androidx.core:core-ktx:1.10.1")
    api("androidx.activity:activity-compose:1.7.2")
//    implementation("androidx.work:work-runtime:2.7.1")
//    implementation("androidx.work:work-runtime-ktx:2.7.1")
}

android {
    compileSdk = 34
    useLibrary("org.apache.http.legacy")

    packagingOptions {
        resources.excludes.add("META-INF/*")
    }

    dexOptions {
        javaMaxHeapSize = "2G"
    }

    sourceSets["main"].manifest.srcFile("src/main/AndroidManifest.xml")
   // sourceSets["main"].kotlin.srcDir("src/main/java")
    defaultConfig {
        applicationId = "cn.minxyzgo.rwpp.android"
        targetSdk = 34
        versionCode = 1
        versionName = "1.0-SNAPSHOT"
        minSdk = 31
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    kotlin {
        jvmToolchain(11)
    }
    namespace = "cn.minxyzgo.rwpp.android"
    buildToolsVersion = "34.0.0"
}