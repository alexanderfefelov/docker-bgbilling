#!/usr/bin/env bash

(cd ../bgscheduler && ./run.sh) \
&& docker logs --follow bgbilling-scheduler
