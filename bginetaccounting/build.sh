#!/bin/bash

INC="$(<"../bg-constants/Dockerfile.inc")" envsubst \$INC < "Dockerfile.in" > Dockerfile
cp --recursive ../lib/ext/container/* container/
docker build --tag alexanderfefelov/bgbilling-accounting .
rm --force --recursive container/lib
rm --force Dockerfile
