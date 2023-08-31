#!/bin/bash

echo "Running git pre-commit hook"

./gradlew :ktlintFormat
