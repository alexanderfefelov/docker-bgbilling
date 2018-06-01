#!/bin/bash

cp --recursive ../dyn/container/* container/
docker build --tag alexanderfefelov/bgbilling-billing .
rm --force --recursive  \
  container/dyn \
  container/lib \
  container/polyglot
