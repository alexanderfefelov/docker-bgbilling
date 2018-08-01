#!/usr/bin/env bash

build() {
    (cd ../$1 && ./build.sh)
}

build bgbilling-base \
&& build bgbilling \
&& build bgscheduler \
&& build bginetaccess \
&& build bginetaccounting \
&& build mysql \
&& build grafana \
&& build populator \
&& build telegraf
