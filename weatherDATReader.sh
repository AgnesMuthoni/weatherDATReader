#!/bin/sh

DIR="$( cd "$( DIR "${BASH_SOURCE[0]}" )" && pwd )"
JARNAME=$DIR/dist/weatherDATReader.jar
start() {
  echo "Starting File read on weather.dat file"

    #logs to be forwaded to stated location. sudo shmod 777 -R required.
  java -jar $JARNAME
}

case "$1" in
    start)
        start
    ;;
    *)
        echo "Usage: fileScanner {start}"
        exit 1
    ;;
esac

exit $?
