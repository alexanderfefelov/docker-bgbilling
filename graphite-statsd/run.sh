#!/usr/bin/env bash

CONTAINER_NAME=bgbilling-graphite-statsd

run() {
    docker run \
      --name $CONTAINER_NAME \
      --detach \
      --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
      --publish 80:80 \
      --publish 2003-2004:2003-2004 \
      --publish 2023-2024:2023-2024 \
      --publish 8125:8125/udp \
      --publish 8126:8126 \
      graphiteapp/graphite-statsd \
    && docker run --rm --link $CONTAINER_NAME:foobar martin/wait -p 80
}

run \
&& docker inspect --format '{{ .NetworkSettings.IPAddress }}' $CONTAINER_NAME
