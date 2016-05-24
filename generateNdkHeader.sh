#!/bin/bash

cd app/build/intermediates/classes/debug/
javah -jni $1
mv ./*.h ../../../../src/main/jni/
