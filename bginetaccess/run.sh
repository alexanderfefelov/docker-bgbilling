#!/bin/bash

docker run --rm --link mysql-master:foobar martin/wait
docker run --rm --link activemq:foobar martin/wait
docker run --rm --link bgbilling:foobar martin/wait

docker run --name bginetaccess --detach \
  --net host \
  --env APP_NAME=BGInetAccess1 \
  --env APP_ID=31415 \
  --env MODULE_ID=1 \
  --env ROOT_DEVICE_ID=2 \
  --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
  --volume bginetaccess:/bginetaccess \
  --publish 67:67/udp \
  --publish 1812:1812/udp \
  alexanderfefelov/bginetaccess
