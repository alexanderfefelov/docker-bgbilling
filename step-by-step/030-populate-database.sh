#!/bin/bash

DATA_DIR=030-populate-database
SOAP_BASE_URL=http://localhost:8080/bgbilling/executer
HTTP_BASE_URL=http://localhost:8080/bgbilling/executer

for dir in $DATA_DIR/010-sql/*; do
  echo $dir
  for file in $dir/*.sql; do
    echo $file
    mysql --host=127.0.0.1 bgbilling < $file
  done
done

while read -r line
do
  if [ -n "$line" ]; then
    IFS=' ' read -a query <<< $line
    echo -n ${query[0]}...
    curl --header "Content-Type: text/xml" --request POST --silent --output /dev/null --write-out "%{http_code}\n" --data-binary @$DATA_DIR/020-soap/${query[0]} $SOAP_BASE_URL/${query[1]}
  fi
done < $DATA_DIR/020-soap/queries

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

while read -r line
do
  if [ -n "$line" ]; then
    IFS=' ' read -a query <<< $line
    echo -n ${query[0]}...
    curl --header "Content-Type: text/xml" --request POST --silent --output /dev/null --write-out "%{http_code}\n" --data-binary @$DATA_DIR/040-soap/${query[0]} $SOAP_BASE_URL/${query[1]}
  fi
done < $DATA_DIR/040-soap/queries
