#!/usr/bin/env bash

docker restart bgbilling-scheduler \
&& docker logs --follow bgbilling-scheduler
