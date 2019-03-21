#!/usr/bin/env bash

CONTAINER_NAME=bgbilling-activemq
HOST_NAME=$CONTAINER_NAME

run() {
    docker run \
      --name $CONTAINER_NAME \
      --hostname $HOST_NAME \
      --detach \
      --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
      --publish 61613:61613 \
      --publish 61616:61616 \
      --publish 8161:8161 \
      alexanderfefelov/bgbilling-activemq \
    && docker run --rm --link $CONTAINER_NAME:foobar martin/wait
}

run \
&& docker inspect --format '{{ .NetworkSettings.IPAddress }}' $CONTAINER_NAME
