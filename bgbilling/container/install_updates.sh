#!/usr/bin/env bash

service bgbilling stop

yes | $BGBILLING_HOME/bg_installer.sh update sets/$UPDATE_SET
