#!/usr/bin/env bash

CONTAINER_NAME=bgbilling-chronograf
HOST_NAME=$CONTAINER_NAME

run() {
    docker run \
      --name $CONTAINER_NAME \
      --hostname $HOST_NAME \
      --detach \
      --volume /etc/localtime:/etc/localtime:ro \
      --volume $CONTAINER_NAME-data:/var/lib/chronograf \
      --volume $CONTAINER_NAME-share:/usr/share/chronograf \
      --publish 8888:8888 \
      --env KAPACITOR_URL="http://kapacitor.bgbilling.local:9092" \
      --env INFLUXDB_URL="http://influxdb.bgbilling.local:8086" \
      alexanderfefelov/bgbilling-chronograf \
    && docker run --rm --link $CONTAINER_NAME:foobar martin/wait -t 600
}

run \
&& docker inspect --format '{{ .NetworkSettings.IPAddress }}' $CONTAINER_NAME
