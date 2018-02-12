#!/bin/bash

docker run --rm --link mysql:mysql martin/wait -p 3306
docker run --rm --link bgbilling:bgbilling martin/wait -p 8080 -t 300

docker run --name populator -it \
  --rm \
  --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
  --link mysql:mysql \
  --link bgbilling:bgbilling \
  alexanderfefelov/populator
