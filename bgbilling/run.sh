#!/bin/bash

function start_mysql_master() {
    CONTAINER_NAME=mysql-master
    docker run \
      --name $CONTAINER_NAME \
      --detach \
      --env SERVER_ID=1 \
      --env MODE=master \
      --env MYSQL_ROOT_PASSWORD=password \
      --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
      --publish 3306:3306 \
      alexanderfefelov/mysql-replication \
    && docker run --rm --link $CONTAINER_NAME:foobar martin/wait -t 300
}

function start_mysql_backup() {
    CONTAINER_NAME=mysql-backup
    docker run \
      --name $CONTAINER_NAME \
      --detach \
      --env SERVER_ID=2 \
      --env MODE=slave \
      --env MYSQL_ROOT_PASSWORD=password \
      --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
      --publish 10002:3306 \
      alexanderfefelov/mysql-replication \
    && docker run --rm --link $CONTAINER_NAME:foobar martin/wait -t 300 \
    && docker exec $CONTAINER_NAME cp /read-only.cnf /etc/mysql/mysql.conf.d/ \
    && docker restart $CONTAINER_NAME \
    && docker run --rm --$CONTAINER_NAME:foobar martin/wait -t 300
}

function start_mysql_slave() {
    CONTAINER_NAME=mysql-slave
    docker run \
      --name $CONTAINER_NAME \
      --detach \
      --env SERVER_ID=3 \
      --env MODE=slave \
      --env MYSQL_ROOT_PASSWORD=password \
      --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
      --publish 10003:3306 \
      alexanderfefelov/mysql-replication \
    && docker run --rm --link $CONTAINER_NAME:foobar martin/wait -t 300 \
    && docker exec $CONTAINER_NAME cp /read-only.cnf /etc/mysql/mysql.conf.d/ \
    && docker restart $CONTAINER_NAME \
    && docker run --rm --link $CONTAINER_NAME:foobar martin/wait -t 300
}

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
      alexanderfefelov/bgbilling \
    && docker run --rm --link $CONTAINER_NAME:foobar martin/wait
}

start_mysql_master
start_mysql_backup
start_mysql_slave
start_activemq
start_bgbilling
