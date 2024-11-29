#!/bin/bash
chmod +x start.sh

# Nettoyer le projet
mvn clean

# Compiler le projet
mvn compile

# Exécuter la classe principale
mvn exec:java -Dexec.mainClass="app.Main"

