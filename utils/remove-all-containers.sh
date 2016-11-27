#!/bin/bash

read -p "All containers will be stopped and removed. Are you sure? " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]
then
    docker rm --force --volumes bginetaccounting
    docker rm --force --volumes bginetaccess
    docker rm --force --volumes bgbilling
    docker rm --force --volumes mysql
    docker rm --force --volumes activemq
fi
