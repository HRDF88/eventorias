import com.android.build.gradle.BaseExtension
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
    alias(libs.plugins.compose.compiler)
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
    id("jacoco")
    id("org.sonarqube") version "4.4.1.3373"


}

// Charger les propriétés du fichier local.properties
val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        load(localPropertiesFile.inputStream())
    }
}

// Récupérer la clé API
val apiKey: String? = localProperties.getProperty("google_api_key")

//Charge la clé release
val keystoreProperties = Properties()
val keystoreFile = rootProject.file("keystore.properties")
if (keystoreFile.exists()) {
    keystoreFile.inputStream().use {
        keystoreProperties.load(it)
    }
}
if (System.getenv("CI") == "true") {
    tasks.whenTaskAdded {
        if (name == "connectedDebugAndroidTest") {
            enabled = false
        }
    }
}


tasks.withType<Test> {
    extensions.configure(JacocoTaskExtension::class) {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*")
    }
}

sonarqube {
    properties {
        property("sonar.organization", "Julien_Seguin") // organisation SonarCloud
        property("sonar.projectKey", "hrdf88")    // identifiant de projet SonarCloud
        property("sonar.projectName", "eventorias") // Nom de  projet SonarCloud
        property("sonar.host.url", "https://sonarcloud.io") // URL de SonarCloud
        property("sonar.login", project.findProperty("sonar.token") ?: "") // Token généré dans SonarCloud
        property("sonar.junit.reportPaths", "build/test-results/testDebugUnitTest/TEST-com.nedrysystems.eventorias.xml")
        property("sonar.coverage.jacoco.xmlReportPaths", "build/test-results/testDebugUnitTest/TEST-com.nedrysystems.eventorias.xml")
    }
}

android {

    namespace = "com.nedrysystems.eventorias"
    compileSdk = 35

    testCoverage {
        version = "0.8.8"
    }

    defaultConfig {
        applicationId = "com.nedrysystems.eventorias"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "GOOGLE_API_KEY", "\"$apiKey\"")
    }
    signingConfigs {
        create("release") {
            // Vérifie si les propriétés sont disponibles
            val storeFilePath = keystoreProperties["storeFile"] as String?
            var storePassword = keystoreProperties["storePassword"] as String?
            var keyAlias = keystoreProperties["keyAlias"] as String?
            var keyPassword = keystoreProperties["keyPassword"] as String?


            if (storeFilePath != null && storePassword != null && keyAlias != null && keyPassword != null) {
                storeFile = file(storeFilePath)
                storePassword = storePassword
                keyAlias = keyAlias
                keyPassword = keyPassword
            } else {

                println("Keystore information is missing, skipping signing configuration.")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
        debug {
            enableAndroidTestCoverage = false
            enableUnitTestCoverage = true

        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        isCoreLibraryDesugaringEnabled = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1,LICENSE.md,LICENSE-notice.md}"
        }
    }
}

val androidExtension = extensions.getByType<BaseExtension>()


// Generate Jacoco HTML/XML reports
val jacocoCoverageReport by tasks.registering(JacocoReport::class) {
    dependsOn("testDebugUnitTest", "createDebugCoverageReport")
    group = "Reporting"
    description = "Generates Jacoco code coverage reports"

    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    val compiledClasses = fileTree(layout.buildDirectory.dir("tmp/kotlin-classes/debug"))
    val sourceDirs = extensions.getByType<BaseExtension>().sourceSets.getByName("main").java.srcDirs

    classDirectories.setFrom(compiledClasses)
    sourceDirectories.setFrom(files(sourceDirs))

    // Exclude generated DI classes (Hilt, Dagger) from coverage
    val excludedPackages = mutableSetOf("**/dagger/**", "**/hilt/**")

    executionData.setFrom(fileTree(layout.buildDirectory) {
        include("**/*.exec", "**/*.ec")
        exclude(excludedPackages)
    })
}




dependencies {
    // Core AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation("androidx.activity:activity-ktx:1.10.1")

    // Compose UI
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation("androidx.compose.material:material:1.7.8")

    // Compose Navigation
    implementation("androidx.navigation:navigation-compose:2.8.9")

    // Firebase (BoM centralise les versions)
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("androidx.browser:browser:1.6.0")
    implementation("com.google.android.gms:play-services-auth:20.7.0")

    //maps
    // Google maps Compose
    implementation("com.google.maps.android:maps-compose:4.3.3")

    // Firebase UI Auth
    implementation("com.firebaseui:firebase-ui-auth:9.0.0")


    // AndroidX Credentials API
    implementation(libs.googleid)

    // Hilt DI
    implementation("com.google.dagger:hilt-android:2.51.1")
    implementation(libs.firebase.messaging.ktx)
    implementation(libs.androidx.media3.common.ktx)
    implementation(libs.androidx.storage)
    implementation(libs.androidx.benchmark.macro)
    implementation(libs.androidx.ui.test.android)
    implementation(libs.androidx.ui.test.junit4.android)
    testImplementation(libs.google.firebase.firestore)
    testImplementation(libs.google.firebase.firestore)
    testImplementation(libs.google.firebase.firestore)
    testImplementation(libs.play.services.measurement.api)
    kapt("com.google.dagger:hilt-compiler:2.51.1")
    implementation("androidx.hilt:hilt-navigation-fragment:1.2.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.51.1")
    androidTestImplementation ("androidx.hilt:hilt-navigation-compose:1.2.0")


    // Coil (images)
    implementation("io.coil-kt:coil:2.3.0")
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Tests
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.0.0")
    testImplementation("org.mockito:mockito-inline:5.2.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation(kotlin("test"))
    androidTestImplementation("org.mockito:mockito-android:5.2.0")
    testImplementation("io.mockk:mockk-android:1.13.7")
    androidTestImplementation("io.mockk:mockk-android:1.13.7")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.0")



    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    testImplementation("com.google.truth:truth:1.4.0")
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    // Firebase Emulator
    androidTestImplementation("com.google.firebase:firebase-firestore-ktx:24.11.0")
    androidTestImplementation("com.google.firebase:firebase-auth-ktx:22.3.1")


    // Java 8+ APIs
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.5")
}
kapt {
    correctErrorTypes = true
}