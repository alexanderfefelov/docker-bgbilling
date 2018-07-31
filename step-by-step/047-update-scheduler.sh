#!/bin/bash

docker exec --tty bgbilling-scheduler /bgbilling/install_updates.sh
docker logs -f bgbilling-scheduler
