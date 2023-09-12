#!/bin/bash

echo "Running git pre-commit hook"

./gradlew lintKotlin

status=$?

if [ $status -ne 0 ]; then
    echo "#######################################################"
    echo "#Lint failed, commit aborted. Please run formatKotlin.#"
    echo "#######################################################"
    exit 1
fi

exit 0
