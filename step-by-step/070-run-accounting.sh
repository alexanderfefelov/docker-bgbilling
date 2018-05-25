#!/bin/bash

(cd ../bginetaccounting && ./run.sh)
docker logs -f bgbilling-accounting
