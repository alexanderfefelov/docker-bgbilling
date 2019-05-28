#!/usr/bin/env bash

(cd ../dnsmasq && ./run.sh) \
&& docker logs --follow bgbilling-dnsmasq
