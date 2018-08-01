#!/bin/bash

docker restart bgbilling-scheduler \
&& docker logs -f bgbilling-scheduler
