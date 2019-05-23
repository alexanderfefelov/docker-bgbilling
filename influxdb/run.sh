#!/usr/bin/env bash

CONTAINER_NAME=bgbilling-influxdb
HOST_NAME=$CONTAINER_NAME

run() {
    docker run \
      --name $CONTAINER_NAME \
      --hostname $HOST_NAME \
      --detach \
      --volume /etc/localtime:/etc/localtime:ro \
      --volume $CONTAINER_NAME-conf:/etc/influxdb:ro \
      --volume $CONTAINER_NAME-data:/var/lib/influxdb \
      --publish 8086:8086 \
      --env GOGC="40" \
      alexanderfefelov/bgbilling-influxdb \
    && docker run --rm --link $CONTAINER_NAME:foobar martin/wait -t 600
}

run \
&& docker inspect --format '{{ .NetworkSettings.IPAddress }}' $CONTAINER_NAME
