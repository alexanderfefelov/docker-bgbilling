#!/usr/bin/env bash

CONTAINER_NAME=bgbilling-dnsmasq
HOST_NAME=$CONTAINER_NAME

run() {
    docker run \
      --name $CONTAINER_NAME \
      --hostname $HOST_NAME \
      --detach \
      --volume /etc/localtime:/etc/localtime:ro \
      --volume $CONTAINER_NAME-etc:/etc:ro \
      --publish 53:53/udp \
      --publish 5380:8080 \
      --log-opt "max-size=100m" \
      --env "HTTP_USER=foo" \
      --env "HTTP_PASS=bar" \
      --restart always \
    alexanderfefelov/bgbilling-dnsmasq \
    && docker run --rm --link $CONTAINER_NAME:foobar martin/wait -t 600
}

run \
&& docker inspect --format '{{ .NetworkSettings.IPAddress }}' $CONTAINER_NAME
