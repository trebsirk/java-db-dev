#!/bin/bash

mvn clean compile package
mvn dependency:copy-dependencies
exit 0

# Define project directories
PROJECT_ROOT="."
SRC_MAIN_JAVA="$PROJECT_ROOT/src/main/java"
SRC_MAIN_RESOURCES="$PROJECT_ROOT/src/main/resources"
BUILD_DIR="$PROJECT_ROOT/build"
META_INF_DIR="$SRC_MAIN_RESOURCES/META-INF"
JAR_FILE="$BUILD_DIR/app.jar"

# Create build directory
mkdir -p "$BUILD_DIR"

# Compile Java files
javac -d "$BUILD_DIR" $(find "$SRC_MAIN_JAVA" -name "*.java")

# Copy resources (including META-INF) to build directory
cp -r "$SRC_MAIN_RESOURCES"/* "$BUILD_DIR"

# Create JAR file
jar cfm "$JAR_FILE" "$META_INF_DIR/MANIFEST.MF" -C "$BUILD_DIR" .

echo "Build complete. JAR file created at $JAR_FILE."
