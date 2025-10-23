// طباعة مكان Gradle
gradle.settingsEvaluated {
    println("Gradle home: " + gradle.gradleUserHomeDir)
}

// استخدام buildscript لتحديد classpath
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.21")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

// تغيير مسار مجلد build الرئيسي
val newBuildDir = rootProject.layout.buildDirectory.dir("../../build").get()
rootProject.layout.buildDirectory.value(newBuildDir)

subprojects {
    val newSubprojectBuildDir = newBuildDir.dir(project.name)
    project.layout.buildDirectory.value(newSubprojectBuildDir)
    project.evaluationDependsOn(":app")
}

// مهمة تنظيف build
tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}