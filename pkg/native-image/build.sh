#!/usr/bin/env bash

set -eu

# Binary name.
bin_name="graal-kotlin-aot"
project_dir=$(dirname "$0")

# Checks if the command exists.
command_exists() {
	command -v "$@" > /dev/null 2>&1
}

if ! ( command_exists native-image ); then
	cat << 'EOF'
Seems like GraalVM is not installed or setup properly on this machine.
Download GraalVM from https://www.graalvm.org/downloads/ and set the
path variable,

 export GRAAL_HOME=/graalvm/install/path/
 export JAVA_HOME=$GRAAL_HOME
 export PATH=$JAVA_HOME/bin:$PATH
EOF
 exit 1
fi

pushd "${project_dir}" > /dev/null

echo -e "\n\033[1;32mBuilding the app...\033[0m"
./gradlew clean shadowJar

echo -e "\n\033[1;32mBuilding the native-image...\033[0m"
native-image -jar build/libs/graal-kotlin-aot-0.0.1-all.jar \
             -H:EnableURLProtocols=http,https  \
             -H:+ReportUnsupportedElementsAtRuntime \
             -H:IncludeResources=META-INF/services/*.* \
             -H:Features=io.sureshg.graalvm.support.JettyFeature \
             -H:Name=${bin_name}
             # -H:ReflectionConfigurationFiles=reflectconfigs/jetty.json \
             # --verbose \
             # --language:js \
             # -H:+JNI \
             # -H:Kind=EXECUTABLE \
             # -H:Class=io.sureshg.MainKt

size=$(stat -f%z ${bin_name})
type=$(file ${bin_name})
echo -e "\n\033[1;37mNative binary is  : \033[1;32m$(realpath ${bin_name}) \033[0m"
echo -e "\033[1;37mFile size         : \033[1;32m$size bytes\033[0m"
echo -e "\033[1;37mFile type         : \033[1;32m$type \033[0m"

popd > /dev/null

