#!/bin/bash

mkdir container
cp --recursive ../dyn/container/* container/
cp --recursive ../lib/ext/container/* container/
docker build --tag alexanderfefelov/bgbilling-base .
rm --force --recursive container
