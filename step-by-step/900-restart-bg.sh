#!/bin/bash

docker stop bginetaccounting
docker stop bginetaccess
docker stop bgbilling

docker start bgbilling
docker run --rm --link bgbilling:bgbilling martin/wait -p 8080 -t 300

docker start bginetaccess
docker start bginetaccounting
