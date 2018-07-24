#!/bin/bash

CONTAINER_NAME=bgbilling-grafana

function run {
    docker run \
      --name $CONTAINER_NAME \
      --detach \
      --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
      --publish 3000:3000 \
      grafana/grafana \
    && docker run --rm --link $CONTAINER_NAME:foobar martin/wait -t 600
}

run \
&& docker inspect --format '{{ .NetworkSettings.IPAddress }}' $CONTAINER_NAME \
&& docker cp container/etc/grafana/provisioning/datasources/*.yaml $CONTAINER_NAME:/etc/grafana/provisioning/datasources/ \
&& docker restart bgbilling-grafana
