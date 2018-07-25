#!/bin/bash

cp --recursive ../jmx/container/* container/
docker build --tag alexanderfefelov/bgbilling-accounting .
rm --force --recursive container/lib
