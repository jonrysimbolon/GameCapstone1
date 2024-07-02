// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.dynamic.feature) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.ksp) apply false
}

/*allprojects {
    tasks.register("printAllDependenciesHtml") {
        doLast {
            val htmlFile = File(buildDir, "reports/dependencies.html")
            htmlFile.parentFile.mkdirs()
            htmlFile.writeText("<html><body><pre>")
            subprojects.forEach { subproject ->
                subproject.configurations.forEach { configuration ->
                    htmlFile.appendText("${subproject.name} - ${configuration.name}\n")
                    configuration.allDependencies.forEach { dependency ->
                        htmlFile.appendText("${dependency.group}:${dependency.name}:${dependency.version}\n")
                    }
                }
            }
            htmlFile.appendText("</pre></body></html>")
        }
    }
}*/

allprojects {
    tasks.register("printAllDependenciesHtml") {
        doLast {
            val htmlFile = File(buildDir, "reports/dependencies.html")
            htmlFile.parentFile.mkdirs()
            htmlFile.writeText("<html><body><pre>")

            // Function to recursively print dependencies
            fun printDependencies(project: Project, depth: Int) {
                val indentation = "\t".repeat(depth)
                project.configurations.forEach { configuration ->
                    htmlFile.appendText("${indentation}${project.name} - ${configuration.name}\n")
                    configuration.allDependencies.forEach { dependency ->
                        htmlFile.appendText("${indentation}${dependency.group}:${dependency.name}:${dependency.version}\n")
                        if (dependency.group == null) {
                            project.rootProject.findProject(dependency.name)
                                ?.let { printDependencies(it, depth + 1) }
                        }
                    }
                }
            }

            // Call printDependencies for each subproject
            subprojects.forEach { subproject ->
                printDependencies(subproject, 0)
            }

            htmlFile.appendText("</pre></body></html>")
        }
    }
}
