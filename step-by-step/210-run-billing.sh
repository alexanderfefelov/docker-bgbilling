#!/usr/bin/env bash

(cd ../bgbilling && ./run.sh) \
&& docker logs --follow bgbilling-billing
