#!/usr/bin/env bash

# Script : https://github.com/micronaut-projects/micronaut-profiles/blob/master/base/features/graal-native-image/skeleton/gradle-build/build-native-image.sh
# Docker : https://github.com/micronaut-projects/micronaut-profiles/blob/master/base/features/graal-native-image/skeleton/gradle-build/Dockerfile
# Options: https://github.com/oracle/graal/blob/master/substratevm/src/com.oracle.svm.core/src/com/oracle/svm/core/SubstrateOptions.java
#
# Linux: GRAAL_HOME/jre/lib/amd64/libsunec.so
# Mac  : GRAAL_HOME/jre/lib/libsunec.dylib
#
# ./gradlew clean build
# -H:ReflectionConfigurationFiles=build/reflect.json \
native-image --no-server \
             --class-path build/libs/kotlin-scratchpad-0.4.0.jar \
             -H:EnableURLProtocols=https \
             -H:IncludeResources="logback.xml|application.yml" \
             -H:Name=kotlin-scratchpad \
             -H:Class=io.sureshg.MainKt \
             -H:Features=io.sureshg.graalvm.support.ReflectionFeature \
             -H:+ReportUnsupportedElementsAtRuntime \
             -H:+ReportExceptionStackTraces \
             -H:+AllowVMInspection \
             --allow-incomplete-classpath \
             --rerun-class-initialization-at-runtime='sun.security.jca.JCAUtil$CachedSecureRandomHolder,javax.net.ssl.SSLContext' \
             --delay-class-initialization-to-runtime=io.netty.handler.codec.http.HttpObjectEncoder,io.netty.handler.codec.http.websocketx.WebSocket00FrameEncoder,io.netty.handler.ssl.util.ThreadLocalInsecureRandom,com.sun.jndi.dns.DnsClient

# upx --brute kotlin-scratchpad

### rsocket-cli example ###
--allow-incomplete-classpath \
-H:+ReportUnsupportedElementsAtRuntime \
-H:InitialCollectionPolicy=com.oracle.svm.core.genscavenge.CollectionPolicy$BySpaceAndTime \
-J-Djava.util.concurrent.ForkJoinPool.common.parallelism=1 \
-H:+PrintAnalysisCallTree \
-H:-AddAllCharsets \
-H:+SpawnIsolates \
-H:-UseServiceLoaderFeature \
-H:+StackTrace \
-Dio.netty.noUnsafe=true \
-Dio.netty.noJdkZlibDecoder=true \
--delay-class-initialization-to-runtime=io.netty.handler.ssl.JdkNpnApplicationProtocolNegotiator,io.netty.handler.ssl.ReferenceCountedOpenSslEngine,io.netty.util.internal.ObjectCleaner,io.netty.handler.ssl.ReferenceCountedOpenSslContext,io.netty.channel.DefaultChannelConfig,io.netty.handler.codec.http.HttpObjectEncoder,io.netty.handler.codec.http.websocketx.WebSocket00FrameEncoder \
-Dfile.encoding=UTF-8
--enable-http
--enable-https
-H:ReflectionConfigurationFiles=reflect.config

#########