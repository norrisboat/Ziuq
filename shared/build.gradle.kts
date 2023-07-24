plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.android.library")
    id("app.cash.sqldelight") version "2.0.0-alpha05"
    id("com.rickclephas.kmp.nativecoroutines") version "1.0.0-ALPHA-4"
    id("com.google.devtools.ksp") version "1.8.0-1.0.8"
    id("dev.icerock.mobile.multiplatform-resources") version "0.23.0"
}

//@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
//    targetHierarchy.default()

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
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
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
                implementation("app.cash.sqldelight:coroutines-extensions:2.0.0-alpha05")

                implementation("io.ktor:ktor-client-core:2.3.2")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.2")
                implementation("io.ktor:ktor-client-logging:2.3.2")
                implementation("io.ktor:ktor-client-content-negotiation:2.3.2")
                implementation("io.ktor:ktor-client-cio:2.3.2")
                implementation("io.ktor:ktor-client-auth:2.3.2")


                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

                implementation("io.insert-koin:koin-core:3.4.0")
                implementation("io.github.aakira:napier:2.6.1")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
                implementation("com.rickclephas.kmm:kmm-viewmodel-core:1.0.0-ALPHA-3")
                implementation("dev.icerock.moko:resources:0.23.0")

                implementation("com.russhwolf:multiplatform-settings:1.0.0")
                implementation("com.russhwolf:multiplatform-settings-no-arg:1.0.0")

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-annotations-common"))
                implementation(kotlin("test-common"))
                implementation("junit:junit:4.13.2")

                implementation("io.insert-koin:koin-test:3.4.0")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-okhttp:2.3.2")
                implementation("app.cash.sqldelight:android-driver:2.0.0-alpha05")
                implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
            }
        }
        /*val androidTest by getting {
            dependencies {
                implementation("app.cash.sqldelight:sqlite-driver:2.0.0-alpha05")

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
                implementation("io.ktor:ktor-client-darwin:2.3.2")
                implementation("app.cash.sqldelight:native-driver:2.0.0-alpha05")
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
}

android {
    namespace = "com.norrisboat.ziuq"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
    }
    sourceSets.getByName("main").res.srcDir(File(buildDir, "generated/moko/androidMain/res"))

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