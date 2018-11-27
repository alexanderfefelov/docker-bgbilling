#!/usr/bin/env bash

CONTAINER_NAME=bgbilling-mysql-backup
HOST_NAME=$CONTAINER_NAME

. functions.sh

run_slave $CONTAINER_NAME $HOST_NAME 2 \
&& docker inspect --format '{{ .NetworkSettings.IPAddress }}' $CONTAINER_NAME
