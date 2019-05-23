#!/usr/bin/env bash

CONTAINER_NAME=bgbilling-telegraf
HOST_NAME=$CONTAINER_NAME

run() {
    docker run \
      --name $CONTAINER_NAME \
      --hostname $HOST_NAME \
      --privileged \
      --detach \
      --volume /etc/localtime:/etc/localtime:ro \
      --volume /var/run/docker.sock:/var/run/docker.sock \
      --volume /:/host:ro \
      --volume /etc:/host/etc:ro \
      --volume /proc:/host/proc:ro \
      --volume /sys:/host/sys:ro \
      --volume /var/run/utmp:/var/run/utmp:ro \
      --volume $CONTAINER_NAME-conf:/etc/telegraf:ro \
      --env HOST_ETC="/host/etc" \
      --env HOST_PROC="/host/proc" \
      --env HOST_SYS="/host/sys" \
      --env HOST_MOUNT_PREFIX="/host" \
      alexanderfefelov/bgbilling-telegraf
}

run \
&& docker inspect --format '{{ .NetworkSettings.IPAddress }}' $CONTAINER_NAME
