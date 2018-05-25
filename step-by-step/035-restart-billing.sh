#!/bin/bash

docker restart bgbilling-billing
docker logs -f bgbilling-billing
