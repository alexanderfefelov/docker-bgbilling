#!/usr/bin/env bash

(cd ../bginetaccess && ./run.sh) \
&& docker logs -f bgbilling-access
