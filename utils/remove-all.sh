#!/bin/bash

read -p "All containers and volumes will be removed. Are you sure? " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]
then
    docker rm --force --volumes bginetaccounting
    docker rm --force --volumes bginetaccess
    docker rm --force --volumes bgbilling
    docker rm --force --volumes activemq
    docker rm --force --volumes mysql-slave
    docker rm --force --volumes mysql-backup
    docker rm --force --volumes mysql-master
fi
