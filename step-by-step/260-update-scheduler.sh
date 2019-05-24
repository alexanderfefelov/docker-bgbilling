#!/usr/bin/env bash

docker exec --tty bgbilling-scheduler /bgbilling/install_updates.sh \
&& docker restart bgbilling-scheduler \
&& docker logs --follow bgbilling-scheduler
