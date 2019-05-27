#!/usr/bin/env bash

CONTAINER_NAME=bgbilling-logstash
HOST_NAME=$CONTAINER_NAME

run() {
    docker run \
      --name $CONTAINER_NAME \
      --hostname $HOST_NAME \
      --detach \
      --volume /etc/localtime:/etc/localtime:ro \
      --volume $CONTAINER_NAME-conf:/usr/share/logstash/config \
      --volume $CONTAINER_NAME-pipeline:/usr/share/logstash/pipeline \
      --publish 5514:5514/udp \
      alexanderfefelov/bgbilling-logstash
}

run \
&& docker inspect --format '{{ .NetworkSettings.IPAddress }}' $CONTAINER_NAME
