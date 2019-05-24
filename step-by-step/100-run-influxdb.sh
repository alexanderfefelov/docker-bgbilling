#!/usr/bin/env bash

(cd ../influxdb && ./run.sh) \
&& docker logs --follow bgbilling-influxdb
