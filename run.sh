#!/bin/bash

# Build the classpath from the dependencies
CLASSPATH=target/db-dev-1.0-SNAPSHOT.jar:$(echo target/dependency/*.jar | tr ' ' ':')

# Run the Java application
java -cp "$CLASSPATH" Main
