#!/bin/bash

function build {
    (cd ../$1 && ./build.sh)
}

build mysql \
&& build bgbilling-base \
&& build bgbilling \
&& build bgscheduler \
&& build bginetaccess \
&& build bginetaccounting \
&& build populator \
&& build telegraf
