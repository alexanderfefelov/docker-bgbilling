#!/bin/bash

service bgscheduler stop

yes | $BGBILLING_HOME/bg_installer.sh update sets/$UPDATE_SET
