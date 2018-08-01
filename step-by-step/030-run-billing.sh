#!/usr/bin/env bash

(cd ../bgbilling && ./run.sh) \
&& docker logs -f bgbilling-billing
