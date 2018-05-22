#!/bin/bash

docker run --rm --link mysql-master:foobar martin/wait
docker run --rm --link activemq:foobar martin/wait
docker run --rm --link bgbilling:foobar martin/wait

docker run --name bginetaccounting --detach \
  --net host \
  --env APP_NAME=BGInetAccounting1 \
  --env APP_ID=314159 \
  --env MODULE_ID=1 \
  --env ROOT_DEVICE_ID=2 \
  --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
  --volume bginetaccounting:/bginetaccounting \
  --publish 1813:1813/udp \
  --publish 2001:2001/udp \
  alexanderfefelov/bginetaccounting
