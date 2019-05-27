#!/usr/bin/env bash

(cd ../portainer && ./run.sh) \
&& docker logs --follow bgbilling-portainer
