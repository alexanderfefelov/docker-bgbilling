#!/bin/bash

CONTAINER_NAME=bgbilling-accounting

function run() {
    docker run \
        --name $CONTAINER_NAME \
        --detach \
        --env APP_NAME=BGInetAccounting1 \
        --env APP_ID=314159 \
        --env MODULE_ID=1 \
        --env ROOT_DEVICE_ID=2 \
        --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
        --volume $CONTAINER_NAME:/bginetaccounting \
        --publish 1813:1813/udp \
        --publish 2001:2001/udp \
        alexanderfefelov/bgbilling-accounting
}

run \
&& docker inspect --format '{{ .NetworkSettings.IPAddress }}' $CONTAINER_NAME
