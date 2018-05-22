#!/bin/bash

function start_activemq() {
    CONTAINER_NAME=activemq
    docker run \
      --name $CONTAINER_NAME \
      --detach \
      --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
      --publish 61616:61616 \
      --publish 8161:8161 \
      rmohr/activemq:5.14.3 \
    && docker run --rm --link $CONTAINER_NAME:foobar martin/wait
}

function start_bgbilling() {
    CONTAINER_NAME=bgbilling
    docker run \
      --name $CONTAINER_NAME \
      --detach \
      --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
      --volume bgbilling:/bgbilling \
      --link mysql-master:mysql \
      --link activemq:activemq \
      --publish 8080:8080 \
      alexanderfefelov/bgbilling
}

start_activemq
start_bgbilling
