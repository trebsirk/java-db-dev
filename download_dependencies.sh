#!/bin/bash

# Define project root and dependency directory
PROJECT_ROOT="."
DEPENDENCY_DIR="$PROJECT_ROOT/lib"

# Ensure the project root and pom.xml exist
if [ ! -d "$PROJECT_ROOT" ]; then
    echo "Error: Project root directory '$PROJECT_ROOT' does not exist."
    exit 1
fi

if [ ! -f "$PROJECT_ROOT/pom.xml" ]; then
    echo "Error: pom.xml not found in '$PROJECT_ROOT'."
    exit 1
fi

# Create the dependency directory if it doesn't exist
mkdir -p "$DEPENDENCY_DIR"

# Change to the project root directory
cd "$PROJECT_ROOT"

# Use Maven to download dependencies and copy them to the dependency directory
mvn dependency:copy-dependencies -DoutputDirectory="$DEPENDENCY_DIR"

# Check if Maven was successful
if [ $? -eq 0 ]; then
    echo "Dependencies downloaded and copied to $DEPENDENCY_DIR."
else
    echo "Error: Failed to download dependencies."
    exit 1
fi
