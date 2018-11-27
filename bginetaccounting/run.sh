#!/usr/bin/env bash

CONTAINER_NAME=bgbilling-accounting
HOST_NAME=$CONTAINER_NAME

run() {
    docker run \
      --name $CONTAINER_NAME \
      --hostname $HOST_NAME \
      --detach \
      --env APP_NAME=BGInetAccounting1 \
      --env APP_ID=314159 \
      --env MODULE_ID=1 \
      --env ROOT_DEVICE_ID=2 \
      --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
      --volume $CONTAINER_NAME:/bginetaccounting \
      --net host \
      alexanderfefelov/bgbilling-accounting
}

run \
&& docker inspect --format '{{ .NetworkSettings.IPAddress }}' $CONTAINER_NAME
