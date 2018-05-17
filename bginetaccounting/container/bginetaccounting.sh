#!/bin/bash

[ -z "$APP_NAME" ] && echo APP_NAME is required && exit 1
[ -z "$APP_ID" ] && echo APP_ID is required && exit 1
[ -z "$MODULE_ID" ] && echo MODULE_ID_ID is required && exit 1
[ -z "$ROOT_DEVICE_ID" ] && echo ROOT_DEVICE_ID is required && exit 1

export BGINETACCOUNTING_HOME=/bginetaccounting

cat $BGINETACCOUNTING_HOME/inet-accounting.xml.template \
  | sed 's@APP_NAME@'"$APP_NAME"'@' \
  | sed 's@APP_ID@'"$APP_ID"'@' \
  | sed 's@MODULE_ID@'"$MODULE_ID"'@' \
  | sed 's@ROOT_DEVICE_ID@'"$ROOT_DEVICE_ID"'@' \
  > $BGINETACCOUNTING_HOME/inet-accounting.xml

$BGINETACCOUNTING_HOME/update.sh

service bginet_accounting start
