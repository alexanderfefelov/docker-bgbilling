#!/bin/bash

../bgbilling/run.sh
docker logs -f bgbilling-billing
