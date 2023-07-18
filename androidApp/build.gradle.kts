plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.devtools.ksp") version "1.8.0-1.0.8"
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
        kotlinCompilerExtensionVersion = "1.4.0"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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
    implementation("androidx.compose.ui:ui:1.3.3")
    implementation("androidx.compose.ui:ui-tooling:1.3.3")
    implementation("androidx.compose.ui:ui-tooling-preview:1.3.3")
    implementation("androidx.compose.foundation:foundation:1.3.1")
    implementation("androidx.compose.material:material:1.3.1")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.0")

    implementation("androidx.compose.material:material:1.4.0-beta01")
    implementation("androidx.compose.material3:material3:1.1.0-alpha06")
    implementation("androidx.navigation:navigation-compose:2.6.0-alpha05")
    implementation("com.google.accompanist:accompanist-navigation-material:0.29.0-alpha")
    implementation("com.google.accompanist:accompanist-navigation-animation:0.29.0-alpha")

    implementation("io.insert-koin:koin-core:3.4.0")
    implementation("io.insert-koin:koin-androidx-compose:3.4.4")

    implementation("io.github.raamcosta.compose-destinations:animations-core:1.8.33-beta")
    ksp("io.github.raamcosta.compose-destinations:ksp:1.8.33-beta")

    implementation("com.rickclephas.kmm:kmm-viewmodel-core:1.0.0-ALPHA-3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.0-beta01")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")

    implementation("dev.icerock.moko:resources-compose:0.23.0")

    implementation("com.github.bumptech.glide:compose:1.0.0-alpha.1")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation(platform("androidx.compose:compose-bom:2022.10.00"))
    implementation("androidx.compose.ui:ui-graphics")
    androidTestImplementation(platform("androidx.compose:compose-bom:2022.10.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}