#!/bin/bash

docker stop bgbilling-accounting
docker stop bgbilling-access
docker stop bgbilling-billing

docker start bgbilling-billing
docker start bgbilling-access
docker start bgbilling-accounting
