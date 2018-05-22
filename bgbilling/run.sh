#!/bin/bash

function run_bgbilling() {
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

run_bgbilling
