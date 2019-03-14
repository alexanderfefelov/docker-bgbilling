#!/usr/bin/env bash

CONTAINER_NAME=bgbilling-vault
HOST_NAME=$CONTAINER_NAME

run() {
    docker run \
      --name $CONTAINER_NAME \
      --hostname $HOST_NAME \
      --detach \
      --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
      --publish 8200:8200 \
      --volume $CONTAINER_NAME-file:/vault/file \
      --volume $CONTAINER_NAME-log:/vault/log \
      --cap-add=IPC_LOCK \
      vault:1.0.3 \
    && docker run --rm --link $CONTAINER_NAME:foobar martin/wait -t 600
}

run \
&& docker inspect --format '{{ .NetworkSettings.IPAddress }}' $CONTAINER_NAME \
