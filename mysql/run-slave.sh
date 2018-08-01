#!/bin/bash

CONTAINER_NAME=bgbilling-mysql-slave

. functions.sh

run_slave $CONTAINER_NAME 3 \
&& docker inspect --format '{{ .NetworkSettings.IPAddress }}' $CONTAINER_NAME
