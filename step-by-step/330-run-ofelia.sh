#!/usr/bin/env bash

(cd ../ofelia && ./run.sh) \
&& docker logs --follow bgbilling-ofelia
