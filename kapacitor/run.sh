#!/usr/bin/env bash

CONTAINER_NAME=bgbilling-kapacitor
HOST_NAME=$CONTAINER_NAME

run() {
    docker run \
      --name $CONTAINER_NAME \
      --hostname $HOST_NAME \
      --detach \
      --volume /etc/localtime:/etc/localtime:ro \
      --volume $CONTAINER_NAME-conf:/etc/kapacitor:ro \
      --volume $CONTAINER_NAME-data:/var/lib/kapacitor \
      --publish 9092:9092 \
      --env KAPACITOR_HOSTNAME="kapacitor.bgbilling.local" \
      --env KAPACITOR_INFLUXDB_0_URLS_0="http://influxdb.bgbilling.local:8086" \
      alexanderfefelov/bgbilling-kapacitor \
    && docker run --rm --link $CONTAINER_NAME:foobar martin/wait -t 600
}

run \
&& docker inspect --format '{{ .NetworkSettings.IPAddress }}' $CONTAINER_NAME
