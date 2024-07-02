import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.exclude

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
    if (project.name == "app") {
        project(":app") {
            tasks.register("printAppDependencies", DependencyReportTask::class)
        }
    }
    if (project.name == "core") {
        project(":core") {
            tasks.register("printCoreDependencies", DependencyReportTask::class)
        }
    }
    if (project.name == "favorite") {
        project(":favorite") {
            tasks.register("printFavoriteDependencies", DependencyReportTask::class)
        }
    }
}



subprojects {
    configurations {
        all {
            exclude(group = "io.netty", module = "netty-handler")
            exclude(group = "io.netty", module = "netty-codec-http")
            exclude(group = "io.netty", module = "netty-codec-http2")
        }
    }
}