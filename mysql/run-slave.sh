#!/bin/bash

CONTAINER_NAME=bgbilling-mysql-slave
TIMEOUT=600

function run() {
    docker run \
      --name $CONTAINER_NAME \
      --detach \
      --env SERVER_ID=3 \
      --env MODE=slave \
      --env MYSQL_ROOT_PASSWORD=password \
      --env MASTER_HOST=master.mysql.bgbilling.local \
      --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
      --volume $CONTAINER_NAME:/var/lib/mysql \
      --publish 10003:3306 \
      alexanderfefelov/bgbilling-mysql \
    && docker run --rm --link $CONTAINER_NAME:foobar martin/wait -t $TIMEOUT \
    && docker exec $CONTAINER_NAME cp /read-only.cnf /etc/mysql/mysql.conf.d/ \
    && docker restart $CONTAINER_NAME \
    && docker run --rm --link $CONTAINER_NAME:foobar martin/wait -t $TIMEOUT
}

run \
&& docker inspect --format '{{ .NetworkSettings.IPAddress }}' $CONTAINER_NAME
