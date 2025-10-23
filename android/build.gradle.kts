import org.gradle.api.tasks.Delete
import org.gradle.kotlin.dsl.*

// طباعة مسار Gradle بشكل صحيح
gradle.settingsEvaluated {
    println("Gradle home: ${gradle.gradleUserHomeDir}")
}

// buildscript بالشكل الصحيح
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.21")
        classpath("com.android.tools.build:gradle:8.11.1") // حط الإصدار المطلوب
    }
}

// كل المشاريع repositories
allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

// تغيير مسار build
val newBuildDir = rootProject.layout.buildDirectory.dir("../../build").get()
rootProject.layout.buildDirectory.set(newBuildDir)

// لكل subprojects
subprojects {
    val newSubprojectBuildDir = newBuildDir.dir(project.name)
    project.layout.buildDirectory.set(newSubprojectBuildDir)
    evaluationDependsOn(":app")
}

// مهمة clean
tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}