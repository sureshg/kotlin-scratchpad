
```
# docker run -it --rm --name jdk13-alpine  openjdk:13-alpine /bin/sh

# Alpine continaers
$ https://mjg123.github.io/2018/11/05/alpine-jdk11-images.html

# Check the module dependencies.
$ jdeps --jdk-internals build/libs/xxxxx.jar

# List all modules
$ java --list-modules
$ jmod describe java.base.jmod
$ jmod list java.base.jmod

# Enable preview feature
$ java[c] --enable-preview

# Alpine mirrors
# https://github.com/alpinelinux/aports/blob/master/main/alpine-mirrors/mirrors.yaml
```