#!/bin/bash

mkdir container
cp --recursive ../dyn/container/* container/
docker build --tag alexanderfefelov/bgbilling-base .
rm --force --recursive container