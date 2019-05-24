#!/usr/bin/env bash

(cd ../bginetaccounting && ./run.sh) \
&& docker logs --follow bgbilling-accounting
