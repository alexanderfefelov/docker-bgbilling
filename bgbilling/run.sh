#!/bin/bash

docker run --name mysql --detach \
  --env MYSQL_ALLOW_EMPTY_PASSWORD=yes \
  --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
  --publish 3306:3306 \
  mysql:5.5.57

docker run --name activemq --detach \
  --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
  --publish 61616:61616 \
  --publish 8161:8161 \
  rmohr/activemq:5.14.3

docker run --rm --link mysql:mysql martin/wait -p 3306
docker run --rm --link activemq:activemq martin/wait -p 61616

docker run --name bgbilling --detach \
  --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
  --volume bgbilling:/bgbilling \
  --link mysql:mysql \
  --link activemq:activemq \
  --publish 8080:8080 \
  alexanderfefelov/bgbilling
