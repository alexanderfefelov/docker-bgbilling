#!/bin/bash

. functions

run_slave bgbilling-mysql-slave 3 \
&& docker inspect --format '{{ .NetworkSettings.IPAddress }}' $CONTAINER_NAME
