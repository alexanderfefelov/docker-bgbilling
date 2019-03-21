#!/usr/bin/env bash

[ -z "$BROKER_NAME" ] && echo BROKER_NAME is required && exit 1

cat activemq.template.xml \
  | sed 's@BROKER__NAME@'"$BROKER_NAME"'@' \
  > container/opt/apache-activemq-5.15.6/activemq.xml

docker build --tag alexanderfefelov/bgbilling-activemq-$BROKER_NAME .
