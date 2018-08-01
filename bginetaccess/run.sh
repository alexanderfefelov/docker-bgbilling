#!/usr/bin/env bash

CONTAINER_NAME=bgbilling-access

run() {
    docker run \
        --name $CONTAINER_NAME \
        --detach \
        --env APP_NAME=BGInetAccess1 \
        --env APP_ID=31415 \
        --env MODULE_ID=1 \
        --env ROOT_DEVICE_ID=2 \
        --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
        --volume $CONTAINER_NAME:/bginetaccess \
        --net host \
    alexanderfefelov/bgbilling-access
}

run \
&& docker inspect --format '{{ .NetworkSettings.IPAddress }}' $CONTAINER_NAME
