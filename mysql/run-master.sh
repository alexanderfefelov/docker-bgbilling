#!/usr/bin/env bash

CONTAINER_NAME=bgbilling-mysql-master
HOST_NAME=$CONTAINER_NAME

. functions.sh

run_master $CONTAINER_NAME $HOST_NAME 1 \
&& docker inspect --format '{{ .NetworkSettings.IPAddress }}' $CONTAINER_NAME
