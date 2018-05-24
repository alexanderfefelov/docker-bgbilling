#!/bin/bash

DATA_DIR=030-populate-database
HTTP_BASE_URL=http://billing.bgbilling.local:8080/bgbilling/executer

for dir in $DATA_DIR/030-http/*; do
  for file in $dir/*.http; do
    echo $file
    while read -r line
    do
      if [[ $line != \#* ]]; then
        echo -n ...
        curl --request GET --silent --output /dev/null --write-out "%{http_code}\n" "$HTTP_BASE_URL?user=admin&pswd=admin&$line"
      fi
    done < $file
  done
done
