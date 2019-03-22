#!/usr/bin/env bash

[ -z "$BROKER_NAME" ] && echo BROKER_NAME is required && exit 1

cat activemq.template.xml \
  | sed 's@BROKER_NAME@'"$BROKER_NAME"'@' \
  > assets/activemq.xml

docker build --tag alexanderfefelov/bgbilling-activemq-$BROKER_NAME .
