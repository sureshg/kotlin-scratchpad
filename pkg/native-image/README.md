Graal Kotlin AOT
================

A kotlin [server][1] app and AOT compile it using [GraalVM][0].
 
### Run
```bash
$ ./build.sh
```
 - Docker
 ```bash 
 $ docker build -t graal-kotlin-aot .
 $ docker run --rm -it -p 7000:7000 graal-kotlin-aot
 ```

```
$ kotlinc -include-runtime  src/main.kt -d main.jar
```

Misc
----

```bash
$ otool -L graal-kotlin-aot
graal-kotlin-aot:
	/usr/lib/libSystem.B.dylib (compatibility version 1.0.0, current version 1252.50.4)
	/System/Library/Frameworks/CoreFoundation.framework/Versions/A/CoreFoundation (compatibility version 150.0.0, current version 1452.23.0)
	/usr/lib/libz.1.dylib (compatibility version 1.0.0, current version 1.2.11)
```

 * Linux
 
 ```bash
 $ ldd graal-kotlin-aot
 ```
 Compile with `--static`
 ```dockerfile
 FROM scratch 
 ADD graal-kotlin-aot /
 ENTRYPOINT ["graal-kotlin-aot"]
 ```
<!-- References -->
[0]: https://www.graalvm.org/
[1]: https://javalin.io/
[2]: https://github.com/JurrianFahner/play-with-graalvm
[3]: https://github.com/graalvm/graalvm-demos/tree/master/java-kotlin-aot

[junit5-gradle-kotlin]: https://git.io/vhsYZ
[graalvm-kts]: https://github.com/mikaelhg/graal-aot-clikt-repro/blob/master/build.gradle.kts
