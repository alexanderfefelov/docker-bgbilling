#!/usr/bin/env bash

[ ! -x "$(which mysql)" ] && echo "MySQL client not found" && exit 1

( \
     cd ../activemq \
  && mysql --host=master.mysql.bgbilling.local --user=root --password=password < init-db.sql \
  && env BROKER_NAME=1 UI_PORT=8161 STOMP_PORT=61613 OPENWIRE_PORT=61616 ./run.sh \
  && env BROKER_NAME=2 UI_PORT=8162 STOMP_PORT=61614 OPENWIRE_PORT=61617 ./run.sh \
  && env BROKER_NAME=3 UI_PORT=8163 STOMP_PORT=61615 OPENWIRE_PORT=61618 ./run.sh \
)
