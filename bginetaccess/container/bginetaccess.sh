#!/bin/bash

[ -z "$APP_NAME" ] && echo APP_NAME is required && exit 1
[ -z "$APP_ID" ] && echo APP_ID is required && exit 1
[ -z "$MODULE_ID" ] && echo MODULE_ID_ID is required && exit 1
[ -z "$ROOT_DEVICE_ID" ] && echo ROOT_DEVICE_ID is required && exit 1

export BGINETACCESS_HOME=/bginetaccess

cat $BGINETACCESS_HOME/inet-access.xml.template \
  | sed 's@APP_NAME@'"$APP_NAME"'@' \
  | sed 's@APP_ID@'"$APP_ID"'@' \
  | sed 's@MODULE_ID@'"$MODULE_ID"'@' \
  | sed 's@ROOT_DEVICE_ID@'"$ROOT_DEVICE_ID"'@' \
  > $BGINETACCESS_HOME//inet-access.xml

$BGINETACCESS_HOME//update.sh

service bginet_access start
