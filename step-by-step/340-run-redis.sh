#!/usr/bin/env bash

(cd ../redis && ./run.sh) \
&& docker logs --follow bgbilling-redis
