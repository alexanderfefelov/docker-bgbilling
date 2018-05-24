#!/bin/bash

docker run --name populator -it \
  --rm \
  --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
  alexanderfefelov/populator
