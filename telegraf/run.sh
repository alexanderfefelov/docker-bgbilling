#!/bin/bash

CONTAINER_NAME=bgbilling-telegraf

function run {
    docker run \
      --name $CONTAINER_NAME \
      --detach \
      --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
      alexanderfefelov/bgbilling-telegraf
}

run \
&& docker inspect --format '{{ .NetworkSettings.IPAddress }}' $CONTAINER_NAME
