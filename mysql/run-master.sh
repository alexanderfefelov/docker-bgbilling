#!/usr/bin/env bash

CONTAINER_NAME=bgbilling-mysql-master

. functions.sh

run_master $CONTAINER_NAME 1 \
&& docker inspect --format '{{ .NetworkSettings.IPAddress }}' $CONTAINER_NAME
