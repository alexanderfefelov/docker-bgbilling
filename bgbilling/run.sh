#!/bin/bash

CONTAINER_NAME=bgbilling

function run() {
    docker run \
      --name $CONTAINER_NAME \
      --detach \
      --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
      --volume $CONTAINER_NAME:/bgbilling \
      --link mysql-master:mysql \
      --link activemq:activemq \
      --publish 8080:8080 \
      alexanderfefelov/bgbilling \
    && docker run --rm --link $CONTAINER_NAME:foobar martin/wait
}

run \
&& docker inspect --format '{{ .NetworkSettings.IPAddress }}' $CONTAINER_NAME
