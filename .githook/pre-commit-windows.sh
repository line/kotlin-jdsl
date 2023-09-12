#!C:/Program\ Files/Git/usr/bin/sh.exe

echo "Running git pre-commit hook"

./gradlew formatKotlin

status=$?

[ $status -ne 0 ] && exit 1
exit 0
