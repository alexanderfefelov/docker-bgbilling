#!/bin/bash

CONTAINER_NAME=bgbilling-activemq

function run {
    docker run \
      --name $CONTAINER_NAME \
      --detach \
      --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
      --publish 61616:61616 \
      --publish 8161:8161 \
      rmohr/activemq:5.14.3 \
    && docker run --rm --link $CONTAINER_NAME:foobar martin/wait
}

run \
&& docker inspect --format '{{ .NetworkSettings.IPAddress }}' $CONTAINER_NAME
