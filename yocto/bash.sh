#!/bin/bash

# Define the paths

JAVA_HOME="/usr/java/jdk-17/bin"
MODULE_PATH="/java/javafx-sdk-22.0.2/lib"
JAR_PATH="/java/MediaPlayer/dist/MediaPlayer.jar"

# Run the Java application
"$JAVA_HOME/java" --module-path "$MODULE_PATH" --add-modules javafx.controls,javafx.fxml,javafx.web,javafx.graphics -jar "$JAR_PATH"
