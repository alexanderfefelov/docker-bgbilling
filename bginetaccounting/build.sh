#!/bin/bash

cp --recursive ../lib/ext/container/* container/
docker build --tag alexanderfefelov/bgbilling-accounting .
rm --force --recursive container/lib
