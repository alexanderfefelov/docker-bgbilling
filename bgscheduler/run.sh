#!/usr/bin/env bash

CONTAINER_NAME=bgbilling-scheduler

run() {
    docker run \
      --name $CONTAINER_NAME \
      --detach \
      --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
      --volume $CONTAINER_NAME:/bgbilling \
      alexanderfefelov/bgbilling-scheduler
}

run \
&& docker inspect --format '{{ .NetworkSettings.IPAddress }}' $CONTAINER_NAME
