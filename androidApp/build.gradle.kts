plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.devtools.ksp") version "1.9.21-1.0.16"
}

android {
    namespace = "com.norrisboat.ziuq.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.norrisboat.ziuq.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.7"
    }
//    packaging {
//        resources {
//            excludes += "/META-INF/{AL2.0,LGPL2.1}"
//        }
//    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    applicationVariants.all {
        kotlin.sourceSets {
            getByName(name) {
                kotlin.srcDir("build/generated/ksp/$name/kotlin")
            }
        }
    }

    applicationVariants.all {
        addJavaSourceFoldersToModel(File(buildDir, "generated/ksp/$name/kotlin"))
    }

    sourceSets.getByName("main").res.srcDir(File(buildDir, "generated/moko/androidMain/res"))
}

dependencies {
    implementation(project(":shared"))
    implementation("androidx.compose.ui:ui:1.5.4")
    implementation("androidx.compose.ui:ui-tooling:1.5.4")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.4")
    implementation("androidx.compose.foundation:foundation:1.5.4")
    implementation("androidx.compose.material:material:1.5.4")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")

    implementation("androidx.compose.material:material:1.5.4")
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation("androidx.navigation:navigation-compose:2.7.6")
    implementation("com.google.accompanist:accompanist-navigation-material:0.32.0")
    implementation("com.google.accompanist:accompanist-navigation-animation:0.29.0-alpha")

    implementation("io.insert-koin:koin-core:3.5.3")
    implementation("io.insert-koin:koin-androidx-compose:3.5.3")

    implementation("io.github.raamcosta.compose-destinations:animations-core:1.9.53")
    ksp("io.github.raamcosta.compose-destinations:ksp:1.9.53")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    implementation("dev.icerock.moko:resources-compose:0.23.0")

    implementation("com.github.bumptech.glide:compose:1.0.0-alpha.6")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation(platform("androidx.compose:compose-bom:2022.10.00"))
    implementation("androidx.compose.ui:ui-graphics")
    androidTestImplementation(platform("androidx.compose:compose-bom:2022.10.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}