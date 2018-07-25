#!/bin/bash

cp --recursive ../jmx/container/lib/* container/lib/
docker build --tag alexanderfefelov/bgbilling-accounting .
rm --force --recursive container/lib
