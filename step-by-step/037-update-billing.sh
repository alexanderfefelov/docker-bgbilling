#!/bin/bash

docker exec --tty bgbilling-billing /bgbilling/install_updates.sh
docker logs -f bgbilling-billing
