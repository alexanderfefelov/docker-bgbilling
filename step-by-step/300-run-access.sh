#!/usr/bin/env bash

(cd ../bginetaccess && ./run.sh) \
&& docker logs --follow bgbilling-access
