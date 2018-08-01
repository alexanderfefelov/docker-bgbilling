#!/usr/bin/env bash

docker restart bgbilling-scheduler \
&& docker logs -f bgbilling-scheduler
