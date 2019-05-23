#!/usr/bin/env bash

build() {
    (cd ../$1 && ./build.sh)
}

build_activemq() {
    (cd ../activemq && env BROKER_NAME=1 ./build.sh && env BROKER_NAME=2 ./build.sh && env BROKER_NAME=3 ./build.sh)
}

build_all() {
    build bgbilling-base \
    && build bgbilling \
    && build bgscheduler \
    && build bginetaccess \
    && build bginetaccounting \
    && build mysql \
    && build_activemq \
    && build grafana \
    && build ofelia \
    && build populator \
    && build telegraf \
    && build influxdb
}

time build_all
