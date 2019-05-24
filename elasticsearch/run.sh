#!/usr/bin/env bash

CONTAINER_NAME=bgbilling-elasticsearch
HOST_NAME=$CONTAINER_NAME

run() {
    docker run \
      --name $CONTAINER_NAME \
      --hostname $HOST_NAME \
      --detach \
      --volume /etc/localtime:/etc/localtime:ro \
      --volume $CONTAINER_NAME-conf:/usr/share/elasticsearch/config \
      --volume $CONTAINER_NAME-data:/usr/share/elasticsearch/data \
      --publish 9200:9200 \
      --publish 9300:9300 \
      --env "discovery.type=single-node" \
      alexanderfefelov/bgbilling-elasticsearch \
    && docker run --rm --link $CONTAINER_NAME:foobar martin/wait -t 600
}

run \
&& docker inspect --format '{{ .NetworkSettings.IPAddress }}' $CONTAINER_NAME
