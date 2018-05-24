#!/bin/bash

CONTAINER_NAME=mysql-slave
TIMEOUT=600
MYSQL_MASTER_HOST=bgbilling.local

function run() {
    docker run \
      --name $CONTAINER_NAME \
      --detach \
      --env SERVER_ID=2 \
      --env MODE=slave \
      --env MYSQL_ROOT_PASSWORD=password \
      --env MASTER_HOST=$MYSQL_MASTER_HOST \
      --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
      --volume $CONTAINER_NAME:/var/lib/mysql \
      --publish 10002:3306 \
      alexanderfefelov/mysql \
    && docker run --rm --link $CONTAINER_NAME:foobar martin/wait -t $TIMEOUT \
    && docker exec $CONTAINER_NAME cp /read-only.cnf /etc/mysql/mysql.conf.d/ \
    && docker restart $CONTAINER_NAME \
    && docker run --rm --link $CONTAINER_NAME:foobar martin/wait -t $TIMEOUT
}

run \
&& docker inspect --format '{{ .NetworkSettings.IPAddress }}' $CONTAINER_NAME
