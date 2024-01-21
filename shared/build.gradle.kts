plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.android.library")
    id("app.cash.sqldelight") version "2.0.1"
    id("com.rickclephas.kmp.nativecoroutines") version "1.0.0-ALPHA-22"
    id("com.google.devtools.ksp") version "1.9.21-1.0.16"
    id("dev.icerock.mobile.multiplatform-resources") version "0.23.0"
}

//@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
//    targetHierarchy.default()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        all {
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
        }
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
                implementation("app.cash.sqldelight:coroutines-extensions:2.0.1")

                implementation("io.ktor:ktor-client-core:2.3.7")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.7")
                implementation("io.ktor:ktor-client-logging:2.3.7")
                implementation("io.ktor:ktor-client-content-negotiation:2.3.7")
                implementation("io.ktor:ktor-client-cio:2.3.7")
                implementation("io.ktor:ktor-client-auth:2.3.7")
                implementation("io.ktor:ktor-client-websockets:2.3.7")

                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")

                implementation("io.insert-koin:koin-core:3.5.3")
                implementation("io.github.aakira:napier:2.7.1")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
                api("com.rickclephas.kmm:kmm-viewmodel-core:1.0.0-ALPHA-16")
                implementation("dev.icerock.moko:resources:0.23.0")

                implementation("com.russhwolf:multiplatform-settings:1.1.1")
                implementation("com.russhwolf:multiplatform-settings-no-arg:1.1.1")

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-annotations-common"))
                implementation(kotlin("test-common"))
                implementation("junit:junit:4.13.2")

                implementation("io.insert-koin:koin-test:3.5.3")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
            }
        }
        val androidMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation("io.ktor:ktor-client-okhttp:2.3.7")
                implementation("app.cash.sqldelight:android-driver:2.0.1")
                implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
            }
        }
        /*val androidTest by getting {
            dependencies {
                implementation("app.cash.sqldelight:sqlite-driver:2.0.1")

                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
                //need to add
                implementation("androidx.test:core:1.5.0")
                implementation("org.robolectric:robolectric:4.9.2")
            }
        }*/
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependencies {
                implementation("io.ktor:ktor-client-darwin:2.3.7")
                implementation("app.cash.sqldelight:native-driver:2.0.1")
            }
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        /*val iosMainTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }*/
    }

    tasks.matching { it.name == "kspKotlinIosArm64" }.configureEach {
        dependsOn(tasks.getByName("generateMRiosArm64Main"))
    }

    tasks.matching { it.name == "kspKotlinIosX64" }.configureEach {
        dependsOn(tasks.getByName("generateMRcommonMain"))
    }

    tasks.matching { it.name == "kspKotlinIosSimulatorArm64" }.configureEach {
        dependsOn(tasks.getByName("generateMRcommonMain"))
    }

    tasks.matching { it.name == "kspKotlinIosSimulatorArm64" }.configureEach {
        dependsOn(tasks.getByName("generateMRiosSimulatorArm64Main"))
    }

    tasks.matching { it.name == "kspKotlinIosX64" }.configureEach {
        dependsOn(tasks.getByName("generateMRiosX64Main"))
    }

    tasks.matching { it.name == "metadataCommonMainProcessResources" }.configureEach {
        dependsOn(tasks.getByName("generateMRcommonMain"))
    }

    kotlin.sourceSets.all {
        languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
    }
}

android {
    namespace = "com.norrisboat.ziuq"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
    }
    sourceSets.getByName("main").res.srcDir(File(buildDir, "generated/moko/androidMain/res"))
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("com.norrisboat.ziuq")
        }
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "com.norrisboat.ziuq"
}