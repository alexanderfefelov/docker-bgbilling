#!/bin/bash

(cd ../bgscheduler && ./run.sh)
docker logs -f bgbilling-scheduler
