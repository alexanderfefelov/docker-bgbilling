#!/usr/bin/env bash

INC="$(<"../bg-constants/Dockerfile.inc")" envsubst \$INC < "Dockerfile.in" > Dockerfile
mkdir container
cp --recursive ../dyn/container/* container/
cp --recursive ../lib/ext/container/* container/
docker build --tag alexanderfefelov/bgbilling-base .
rm --force --recursive container
rm --force Dockerfile
