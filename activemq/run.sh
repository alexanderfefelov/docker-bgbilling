#!/usr/bin/env bash

[ -z "$BROKER_NAME" ] && echo BROKER_NAME is required && exit 1
[ -z "$UI_PORT" ] && echo UI_PORT is required && exit 1
[ -z "$STOMP_PORT" ] && echo STOMP_PORT is required && exit 1
[ -z "$OPENWIRE_PORT" ] && echo OPENWIRE_PORT is required && exit 1

CONTAINER_NAME=bgbilling-activemq-$BROKER_NAME
HOST_NAME=$CONTAINER_NAME-$BROKER_NAME

run() {
    docker run \
      --name $CONTAINER_NAME \
      --hostname $HOST_NAME \
      --detach \
      --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
      --publish $STOMP_PORT:61613 \
      --publish $OPENWIRE_PORT:61616 \
      --publish $UI_PORT:8161 \
      alexanderfefelov/bgbilling-activemq-$BROKER_NAME
}

run \
&& docker inspect --format '{{ .NetworkSettings.IPAddress }}' $CONTAINER_NAME
