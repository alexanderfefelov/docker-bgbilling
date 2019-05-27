#!/usr/bin/env bash

(cd ../chronograf && ./run.sh) \
&& docker logs --follow bgbilling-chronograf
