#!/bin/bash

read -p "All containers and volumes will be removed. Are you sure? " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]
then
    docker rm --force --volumes bginetaccounting
    docker volume rm bginetaccounting

    docker rm --force --volumes bginetaccess
    docker volume rm bginetaccess

    docker rm --force --volumes bgbilling
    docker volume rm bgbilling

    docker rm --force --volumes mysql
    docker rm --force --volumes activemq
fi