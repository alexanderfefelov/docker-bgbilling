#!/usr/bin/env bash

docker exec --tty bgbilling-billing /bgbilling/install_updates.sh \
&& docker restart bgbilling-billing \
&& docker logs -f bgbilling-billing
