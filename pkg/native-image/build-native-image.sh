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