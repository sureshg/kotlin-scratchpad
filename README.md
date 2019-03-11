Kotlin-ScratchPad
=================
[![Build Status](https://travis-ci.org/sureshg/kotlin-scratchpad.svg?branch=master)](https://travis-ci.org/sureshg/kotlin-scratchpad)
[![Build status](https://ci.appveyor.com/api/projects/status/bryiiki9fdt6vo60?svg=true)](https://ci.appveyor.com/project/sureshg/kotlin-scratchpad)
[![Docker Hub](https://img.shields.io/docker/pulls/sureshg/kotlin-demo.svg?label=Docker%20Hub&logo=docker&style=flat-square)][docker-hub]
[![codecov](https://codecov.io/gh/sureshg/kotlin-scratchpad/branch/master/graph/badge.svg)](https://codecov.io/gh/sureshg/kotlin-scratchpad)
[![GitHub release](https://img.shields.io/github/release/JetBrains/kotlin.svg?style=flat-square&label=Kotlin)](https://github.com/JetBrains/kotlin/releases/latest)
[![Sonar Cloud](https://sonarcloud.io/api/project_badges/measure?project=io.sureshg%3Akotlin-scratchpad&metric=alert_status)](https://sonarcloud.io/dashboard?id=io.sureshg%3Akotlin-scratchpad)

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
[docker-hub]: https://hub.docker.com/r/sureshg/kotlin-demo
[junit5-kotlin]: https://github.com/junit-team/junit5-samples/tree/master/junit5-jupiter-starter-gradle-kotlin
[coda's-common-pom]: https://github.com/codahale/common-pom
[Deps Shield]: https://depshield.sonatype.org/badges/sureshg/kotlin-scratchpad/depshield.svg

<!-- Gradle Kotlin DSL References -->
[10]: https://blog.gradle.org/kotlin-dsl-1.0
[11]: https://github.com/yschimke/okurl/blob/master/build.gradle.kts
[12]: https://docs.gradle.org/current/userguide/publishing_maven.html
[13]: https://docs.gradle.org/current/userguide/signing_plugin.html#signing_plugin
[14]: https://medium.com/@nmauti/sign-and-publish-on-maven-central-a-project-with-the-new-maven-publish-gradle-plugin-22a72a4bfd4b

