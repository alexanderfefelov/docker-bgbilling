#!/usr/bin/env bash

pwd
echo ${BASH_SOURCE[0]}

echo foo

/ofelia/scripts/bgbilling-mysql-backup-rotate.sh

echo baz
