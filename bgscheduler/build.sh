#!/bin/bash

cp --recursive ../dyn/container/* container/
docker build --tag alexanderfefelov/bgbilling-scheduler .
rm --force --recursive  \
  container/dyn \
  container/lib \
  container/polyglot
