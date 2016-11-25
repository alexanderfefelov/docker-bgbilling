#!/bin/bash

docker restart bgbilling
docker logs -f bgbilling
