#!/bin/bash

# Define project root and default directories
PROJECT_ROOT="."
SRC_MAIN_JAVA="$PROJECT_ROOT/src/main/java"
SRC_MAIN_RESOURCES="$PROJECT_ROOT/src/main/resources"
SRC_MAIN_META_INF="$SRC_MAIN_RESOURCES/META-INF"
SRC_TEST_JAVA="$PROJECT_ROOT/src/test/java"
SRC_TEST_RESOURCES="$PROJECT_ROOT/src/test/resources"

# Create project root directory
mkdir -p "$PROJECT_ROOT"

# Create source directories
mkdir -p "$SRC_MAIN_JAVA"
mkdir -p "$SRC_MAIN_RESOURCES"
mkdir -p "$SRC_MAIN_META_INF"
mkdir -p "$SRC_TEST_JAVA"
mkdir -p "$SRC_TEST_RESOURCES"

# Create default Main.java file
MAIN_JAVA_FILE="$SRC_MAIN_JAVA/Main.java"
cat <<EOL > "$MAIN_JAVA_FILE"
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
EOL

# Create default META-INF/MANIFEST.MF file
MANIFEST_FILE="$SRC_MAIN_META_INF/MANIFEST.MF"
cat <<EOL > "$MANIFEST_FILE"
Manifest-Version: 1.0
Main-Class: Main
EOL

# Create default .gitignore file
GITIGNORE_FILE="$PROJECT_ROOT/.gitignore"
cat <<EOL > "$GITIGNORE_FILE"
# Compiled class file
*.class

# Log file
*.log

# BlueJ files
*.ctxt

# Mobile Tools for Java (J2ME)
.mtj.tmp/

# Package Files #
*.jar
*.war
*.nar
*.ear
*.zip
*.tar.gz
*.rar

# virtual machine crash logs, see http://www.java.com/en/download/help/error_hotspot.xml
hs_err_pid*
EOL

# Create README.md file
README_FILE="$PROJECT_ROOT/README.md"
cat <<EOL > "$README_FILE"
# My Java Project

This is a simple Java project template.
EOL

echo "Java project structure created successfully."
