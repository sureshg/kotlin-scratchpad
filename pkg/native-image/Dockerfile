FROM ensignprojects/graalvm as native-image
ARG app_name=graal-kotlin-aot
ARG app_version=0.0.1
ADD . /app
RUN cd /app \
    && ./gradlew clean shadowJar \
    && native-image -jar build/libs/${app_name}-${app_version}-all.jar \
                -H:EnableURLProtocols=https,https  \
                -H:+ReportUnsupportedElementsAtRuntime \
                -H:ReflectionConfigurationFiles=reflectconfigs/jetty.json \
                -H:Name=${app_name}
                # --verbose \
                # -H:+JNI \
                # -H:Kind=EXECUTABLE \
                # -H:Class=io.sureshg.MainKt

FROM scratch
ARG app_name=graal-kotlin-aot
ARG server_port=7000
COPY --from=native-image /app/$app_name /app/$app_name
COPY --from=native-image /lib64 /lib64
COPY --from=native-image /lib /lib
COPY --from=native-image /bin/sh /bin/sh
EXPOSE $server_port
CMD /app/$app_name