#!/bin/bash

../bgscheduler/run.sh
docker logs -f bgbilling-scheduler
