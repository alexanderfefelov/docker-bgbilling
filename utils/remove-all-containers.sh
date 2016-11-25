#!/bin/bash

read -p "All containers will be stopped and removed. Are you sure? " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]
then
    docker stop bginetaccounting
    docker stop bginetaccess
    docker stop bgbilling
    docker stop mysql
    docker stop activemq
    docker rm bginetaccounting
    docker rm bginetaccess
    docker rm bgbilling
    docker rm mysql
    docker rm activemq
fi
