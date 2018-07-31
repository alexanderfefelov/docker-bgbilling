#!/bin/bash

service bgbilling stop

cd $BGBILLING_HOME
yes | ./bg_installer.sh update sets/$UPDATE_SET

service bgbilling start
