#!/bin/bash

../mysql/run.sh
../bgbilling/run.sh
docker logs -f bgbilling
