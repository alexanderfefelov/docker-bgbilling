#!/usr/bin/env bash

(cd ../vault && ./run.sh) \
&& docker logs --follow bgbilling-vault
