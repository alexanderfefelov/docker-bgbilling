#!/usr/bin/env bash

docker run \
  --name populator \
  --interactive \
  --tty \
  --rm \
  --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
  alexanderfefelov/bgbilling-populator
