#!/usr/bin/env bash

build() {
    (cd ../$1 && ./build.sh)
}

build_all() {
    build bgbilling-base \
    && build bgbilling \
    && build bgscheduler \
    && build bginetaccess \
    && build bginetaccounting \
    && build mysql \
    && build activemq \
    && build grafana \
    && build ofelia \
    && build populator \
    && build telegraf
}

time build_all
