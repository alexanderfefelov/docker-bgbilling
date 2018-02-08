#!/bin/bash

docker run --rm --link mysql:mysql martin/wait -p 3306
docker run --rm --link bgbilling:bgbilling martin/wait -p 8080 -t 300

docker run --name populator -ti --rm alexanderfefelov/populator
