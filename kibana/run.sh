#!/usr/bin/env bash

CONTAINER_NAME=bgbilling-kibana
HOST_NAME=$CONTAINER_NAME

run() {
    docker run \
      --name $CONTAINER_NAME \
      --hostname $HOST_NAME \
      --detach \
      --volume /etc/localtime:/etc/localtime:ro \
      --volume $CONTAINER_NAME-conf:/usr/share/kibana/config \
      --volume $CONTAINER_NAME-data:/usr/share/kibana/data \
      --publish 5601:5601 \
      alexanderfefelov/bgbilling-kibana \
    && docker run --rm --link $CONTAINER_NAME:foobar martin/wait -t 600
}

run \
&& docker inspect --format '{{ .NetworkSettings.IPAddress }}' $CONTAINER_NAME
