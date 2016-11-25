#!/bin/bash

docker run --rm --link mysql:mysql martin/wait -p 3306
docker run --rm --link activemq:activemq martin/wait -p 61616
docker run --rm --link bgbilling:bgbilling martin/wait -p 8080 -t 300

docker run --name bginetaccounting --detach \
  --env APP_NAME=BGInetAccounting1 \
  --env APP_ID=314159 \
  --env MODULE_ID=1 \
  --env ROOT_DEVICE_ID=1 \
  --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
  --link mysql:mysql \
  --link activemq:activemq \
  --publish 1813:1813/udp \
  --publish 2001:2001/udp \
  alexanderfefelov/docker-bginetaccounting
