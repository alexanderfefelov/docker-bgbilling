#!/bin/bash

CONTAINER_NAME=bgbilling-billing

function run {
    docker run \
      --name $CONTAINER_NAME \
      --detach \
      --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
      --volume $CONTAINER_NAME:/bgbilling \
      --publish 8080:8080 \
      alexanderfefelov/bgbilling-billing
}

run \
&& docker inspect --format '{{ .NetworkSettings.IPAddress }}' $CONTAINER_NAME
