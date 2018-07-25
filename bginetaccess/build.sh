#!/bin/bash

cp --recursive ../jmx/container/* container/
docker build --tag alexanderfefelov/bgbilling-access .
rm --force --recursive container/lib
