#!/bin/bash

MYSQL_MASTER_IP_ADDRESS=192.168.99.254
TIMEOUT=600

function run_mysql_master() {
    CONTAINER_NAME=mysql-master
    docker run \
      --name $CONTAINER_NAME \
      --detach \
      --env SERVER_ID=1 \
      --env MODE=master \
      --env MYSQL_ROOT_PASSWORD=password \
      --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
      --publish 3306:3306 \
      alexanderfefelov/mysql \
    && docker run --rm --link $CONTAINER_NAME:foobar martin/wait -t $TIMEOUT
}

function run_mysql_backup() {
    CONTAINER_NAME=mysql-backup
    docker run \
      --name $CONTAINER_NAME \
      --detach \
      --env SERVER_ID=2 \
      --env MODE=slave \
      --env MYSQL_ROOT_PASSWORD=password \
      --env MASTER_HOST=$MYSQL_MASTER_IP_ADDRESS \
      --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
      --publish 10002:3306 \
      alexanderfefelov/mysql \
    && docker run --rm --link $CONTAINER_NAME:foobar martin/wait -t 300 \
    && docker exec $CONTAINER_NAME cp /read-only.cnf /etc/mysql/mysql.conf.d/ \
    && docker restart $CONTAINER_NAME \
    && docker run --rm --link $CONTAINER_NAME:foobar martin/wait -t $TIMEOUT
}

function run_mysql_slave() {
    CONTAINER_NAME=mysql-slave
    docker run \
      --name $CONTAINER_NAME \
      --detach \
      --env SERVER_ID=3 \
      --env MODE=slave \
      --env MYSQL_ROOT_PASSWORD=password \
      --env MASTER_HOST=$MYSQL_MASTER_IP_ADDRESS \
      --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
      --publish 10003:3306 \
      alexanderfefelov/mysql \
    && docker run --rm --link $CONTAINER_NAME:foobar martin/wait -t $TIMEOUT \
    && docker exec $CONTAINER_NAME cp /read-only.cnf /etc/mysql/mysql.conf.d/ \
    && docker restart $CONTAINER_NAME \
    && docker run --rm --link $CONTAINER_NAME:foobar martin/wait -t $TIMEOUT
}

run_mysql_master
run_mysql_backup
run_mysql_slave
