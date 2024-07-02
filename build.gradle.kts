// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.dynamic.feature) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.ksp) apply false
}

allprojects {
    tasks.register("printAllDependencies", DependencyReportTask::class)
}

configurations {
    all {
        exclude(group = "com.google.guava", module = "listenablefuture")
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib")
        exclude(group = "io.netty", module = "netty-handler")
        exclude(group = "io.netty", module = "netty-codec-http2")
    }
}