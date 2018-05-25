#!/bin/bash

. functions

run_slave bgbilling-mysql-backup 2 \
&& docker inspect --format '{{ .NetworkSettings.IPAddress }}' $CONTAINER_NAME
