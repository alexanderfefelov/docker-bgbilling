#!/usr/bin/env bash

(cd ../telegraf && ./run.sh) \
&& docker logs --follow bgbilling-telegraf
