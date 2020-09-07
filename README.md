:wine_glass: Kotlin-ScratchPad
=================
[![GitHub Workflow Status][shieldio_img]][gha_url] 
[![Kotlin release][kt_img]][kt_url] 
[![OpenJDK Version][java_img]][java_url] 
[![Docker Hub][docker_img]][docker_url]
[![Sonar][sonar_img]][sonar_url]
[![Style guide][sty_img]][sty_url]
   
[java_url]: https://jdk.java.net/
[java_img]: https://img.shields.io/badge/OpenJDK-jdk--14-red?logo=java&style=for-the-badge&logoColor=red

[kt_url]: https://github.com/JetBrains/kotlin/releases/latest
[kt_img]: https://img.shields.io/github/release/JetBrains/kotlin.svg?label=Kotlin&logo=kotlin&style=for-the-badge

[sty_url]: https://kotlinlang.org/docs/reference/coding-conventions.html
[sty_img]: https://img.shields.io/badge/style-Kotlin--Official-40c4ff.svg?style=for-the-badge&logo=kotlin&logoColor=40c4ff 

[gha_url]: https://github.com/sureshg/kotlin-scratchpad/actions
[gha_img]: https://github.com/sureshg/kotlin-scratchpad/workflows/Gradle%20Build/badge.svg?branch=master                         
[shieldio_img]: https://img.shields.io/github/workflow/status/sureshg/kotlin-scratchpad/Gradle%20Build?color=green&label=Build&logo=Github-Actions&logoColor=green&style=for-the-badge

[docker_img]: https://img.shields.io/docker/v/sureshg/kotlin-demo?color=40c4ff&label=DockerHub&logo=docker&logoColor=40c4ff&style=for-the-badge 
[docker_url]: https://hub.docker.com/r/sureshg/kotlin-demo

[sonar_img]: https://img.shields.io/sonar/quality_gate/io.sureshg:kotlin-scratchpad?logo=sonarcloud&server=https%3A%2F%2Fsonarcloud.io&style=for-the-badge
[sonar_url]: https://sonarcloud.io/dashboard?id=io.sureshg%3Akotlin-scratchpad

Build 
-----

 - For building the executable jar, run
 
   ```bash
   $ ./gradlew clean build
 
   # For checking dependency updates.
   # ./gradlew clean dependencyUpdates
   
   # For publishing to maven repo
   # ./gradlew clean publish
   
   # For publishing shaded jar
   # ./gradlew clean publish -Pshaded=true
   
   # For creating dist tar
   # ./gradlew clean distTar
   ```
   
 - For building and pushing `Docker` image, run 
   
   ```bash
   # Image with tags as current artifact version and "latest"
   $ ./gradlew jib
   ```
   
 :whale: [Docker Hub URL][docker-hub]

Run
---

```bash
$ docker pull sureshg/kotlin-demo:latest
$ docker run -it --rm --name kotlin-demo sureshg/kotlin-demo:latest
```

<!--- Badges --->
[0]: https://github.com/guenhter/kotlin-unit-testing
[1]: https://github.com/mockito/mockito/wiki/What%27s-new-in-Mockito-2#mock-the-unmockable-opt-in-mocking-of-final-classesmethods
[2]: https://microbadger.com/images/sureshg/kotlin-demo:latest

[install-jdk.sh]: https://github.com/sormuras/sormuras.github.io/blob/master/.travis.yml
[junit5-kotlin]: https://github.com/junit-team/junit5-samples/tree/master/junit5-jupiter-starter-gradle-kotlin
[coda's-common-pom]: https://github.com/codahale/common-pom
[Deps Shield]: https://depshield.sonatype.org/badges/sureshg/kotlin-scratchpad/depshield.svg


