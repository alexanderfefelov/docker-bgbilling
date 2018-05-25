#!/bin/bash

CONTAINER_NAME=bgbilling-mysql-backup

. functions

run_slave $CONTAINER_NAME 2 \
&& docker inspect --format '{{ .NetworkSettings.IPAddress }}' $CONTAINER_NAME
