#!/usr/bin/env bash

(cd ../kapacitor && ./run.sh) \
&& docker logs --follow bgbilling-kapacitor
