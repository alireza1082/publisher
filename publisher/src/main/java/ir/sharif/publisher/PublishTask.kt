package ir.sharif.publisher

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.provider.Property
import org.gradle.api.publish.maven.MavenPublication
import java.net.URI

interface PluginExtension : MavenPublication {
    val artifact: Property<String>
}

class PublishTask : Plugin<Project> {
    override fun apply(target: Project) {
        val extensionMaven = target.extensions.create("maven", PluginExtension::class.java)
        extensionMaven.artifactId = extensionMaven.artifact.get()
        target.afterEvaluate {
            extensionMaven.from(target.components.getByName("release"))
        }

        val extensionMavenSnapshot =
            target.extensions.create("mavenSnapshot", PluginExtension::class.java)
        extensionMavenSnapshot.artifactId = extensionMavenSnapshot.artifact.get()
        target.afterEvaluate {
            extensionMavenSnapshot.from(target.components.getByName("release"))
        }
        target.repositories.add(target.repositories.mavenRepository(project = target))
    }

    private fun getRepoUrl(project: Project): String =
        (project.properties["artifactoryUrl"] as String) + "/" +
                if (project.properties["isRelease"] == "true")
                    (project.properties["releaseRepoKey"] as String)
                else
                    (project.properties["snapshotRepoKey"] as String)

    private fun RepositoryHandler.mavenRepository(project: Project): MavenArtifactRepository {
        return maven {
            it.url = URI(getRepoUrl(project))
            it.isAllowInsecureProtocol = true
            it.credentials { pass ->
                pass.username = project.properties["mavenUsername"].toString()
                pass.password = project.properties["mavenPassword"].toString()
            }
        }
    }
}
