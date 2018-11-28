#!/usr/bin/env bash

CONTAINER_NAME=bgbilling-ofelia
HOST_NAME=$CONTAINER_NAME

run() {
    docker run \
      --name $CONTAINER_NAME \
      --hostname $HOST_NAME \
      --detach \
      --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
      --volume /var/run/docker.sock:/var/run/docker.sock \
      --volume $CONTAINER_NAME:/ofelia \
      alexanderfefelov/bgbilling-ofelia
}

run \
&& docker inspect --format '{{ .NetworkSettings.IPAddress }}' $CONTAINER_NAME
