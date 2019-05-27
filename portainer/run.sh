#!/usr/bin/env bash

CONTAINER_NAME=bgbilling-portainer
HOST_NAME=$CONTAINER_NAME

run() {
    docker run \
      --name $CONTAINER_NAME \
      --hostname $HOST_NAME \
      --detach \
      --volume /etc/localtime:/etc/localtime:ro \
      --volume /var/run/docker.sock:/var/run/docker.sock \
      --volume $CONTAINER_NAME-data:/data \
      --publish 9000:9000 \
      portainer/portainer \
      --admin-password='$2y$05$R2EGk7vv9A14vBLkugY7b.ZGCHksFivPAzgTVrz8bVSDB0dqFq57O' \
    && docker run --rm --link $CONTAINER_NAME:foobar martin/wait -t 600
}

run \
&& docker inspect --format '{{ .NetworkSettings.IPAddress }}' $CONTAINER_NAME \
