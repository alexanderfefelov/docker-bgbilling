#!/bin/bash

docker run --rm --link mysql-master:foobar martin/wait -p 3306
docker run --rm --link bgbilling:foobar martin/wait -p 8080 -t 300

docker run --name populator -it \
  --rm \
  --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
  --link mysql-master:mysql \
  --link bgbilling:bgbilling \
  alexanderfefelov/populator
