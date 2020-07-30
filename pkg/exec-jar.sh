#!/bin/bash

# cat exec-jar.sh ./target/my-app-1.0.0.jar > my-app
# chmod +x my-app

SOURCE="${BASH_SOURCE[0]}"
while [[ -h "$SOURCE" ]]; do # resolve $SOURCE until the file is no longer a symlink
  DIR="$( cd -P "$( dirname "$SOURCE" )" >/dev/null && pwd )"
  SOURCE="$(readlink "$SOURCE")"
  [[ ${SOURCE} != /* ]] && SOURCE="$DIR/$SOURCE" # if $SOURCE was a relative symlink, we need to resolve it relative to the path where the symlink file was located
done
DIR="$( cd -P "$( dirname "$SOURCE" )" >/dev/null && pwd )"

EXEC=$(basename "$0")
SELF="$DIR/$EXEC"

exec java -jar "${SELF}" "$@"
exit 1
