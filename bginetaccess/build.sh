#!/bin/bash

cp --recursive ../lib/ext/container/* container/
cp --recursive ../lib/jmx/container/* container/
docker build --tag alexanderfefelov/bgbilling-access .
rm --force --recursive container/lib
