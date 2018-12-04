#!/usr/bin/env bash

docker restart bgbilling-billing \
&& docker logs --follow bgbilling-billing
