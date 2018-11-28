#!/usr/bin/env bash

(cd ../ofelia && ./run.sh) \
&& docker logs -f bgbilling-ofelia
