
buildscript {
  repositories {
    mavenCentral()
    google()
    gradlePluginPortal()
  }
  dependencies {
    classpath(libs.plugin.android.cache.fix)
    classpath(libs.plugin.androidgradleplugin)
    classpath(libs.plugin.dokka)
    classpath(libs.plugin.kotlin)
    classpath(libs.plugin.licensee)
    classpath(libs.plugin.paparazzi)
    classpath(libs.plugin.publish)
  }
}

plugins {
  alias(libs.plugins.codequalitytools)
	java
	kotlin("jvm") version "1.6.0"
	`maven-publish`
}

publishing {
	publications {
		create<MavenPublication>("maven") {
			groupId = "com.cube"
			artifactId = "cropper.image"
			version = "5.0.0"

			from(components["java"])
		}
	}
}

codeQualityTools {
  checkstyle {
    enabled = false // Kotlin only.
  }
  pmd {
    enabled = false // Kotlin only.
  }
  ktlint {
    toolVersion = libs.versions.ktlint.get()
    experimental = true
  }
  detekt {
    enabled = false // Don"t want.
  }
  cpd {
    enabled = false // Kotlin only.
  }
  lint {
    lintConfig = rootProject.file("lint.xml")
    checkAllWarnings = true
  }
  kotlin {
    allWarningsAsErrors = true
  }
}

allprojects {
  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
  }
}

subprojects {
  plugins.withType<com.android.build.gradle.api.AndroidBasePlugin> {
    apply(plugin = "org.gradle.android.cache-fix")
  }

  tasks.withType(Test::class.java).all {
    testLogging.exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
  }
  tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
      jvmTarget = "1.8"
    }
  }
}
