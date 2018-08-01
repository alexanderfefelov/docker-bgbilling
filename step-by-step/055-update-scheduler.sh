#!/bin/bash

docker exec --tty bgbilling-scheduler /bgbilling/install_updates.sh \
&& docker restart bgbilling-scheduler \
&& docker logs -f bgbilling-scheduler
