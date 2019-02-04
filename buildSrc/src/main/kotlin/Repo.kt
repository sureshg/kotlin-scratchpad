import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPom


sealed class Repo(val id: String, val url: String)
object Jitpack : Repo("jitpack", "https://jitpack.io")
object KotlinEap : Repo("kotlin-eap", "https://dl.bintray.com/kotlin/kotlin-eap")
object GradlePlugins : Repo("gradle_plugin", "https://plugins.gradle.org/m2/")
object SonatypeSnapshots : Repo("sonatype-snapshots", "https://oss.sonatype.org/content/repositories/snapshots/")
object SonatyperReleases : Repo("sonatype-releases", "https://oss.sonatype.org/content/repositories/releases/")

/**
 * Checks if it's snapshot version string.
 */
fun Any?.isSnapshot() = toString().endsWith("SNAPSHOT", true)

/**
 * Add the all maven runtime dependencies to pom.
 *
 * @param [project] gradle project.
 */
fun MavenPom.addDependencies(project: Project) = withXml {
    asNode().appendNode("dependencies").let { depNode ->
        project.configurations.named("implementation").get().allDependencies.forEach {
            depNode.appendNode("dependency").apply {
                appendNode("groupId", it.group)
                appendNode("artifactId", it.name)
                appendNode("version", it.version)
            }
        }
    }
}