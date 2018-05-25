#!/bin/bash

CONTAINER_NAME=bgbilling-mysql-master

function run {
    docker run \
      --name $CONTAINER_NAME \
      --detach \
      --env SERVER_ID=1 \
      --env MODE=master \
      --env MYSQL_ROOT_PASSWORD=password \
      --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
      --volume $CONTAINER_NAME:/var/lib/mysql \
      --publish 3306:3306 \
      alexanderfefelov/bgbilling-mysql \
    && docker run --rm --link $CONTAINER_NAME:foobar martin/wait -t 600
}

run \
&& docker inspect --format '{{ .NetworkSettings.IPAddress }}' $CONTAINER_NAME
