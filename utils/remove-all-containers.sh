#!/bin/bash

read -p "All containers and volumes will be removed. Are you sure? " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]
then
    docker rm --force --volumes bgbilling-accounting
    docker volume rm bgbilling-accounting

    docker rm --force --volumes bgbilling-access
    docker volume rm bgbilling-access

    docker rm --force --volumes bgbilling-scheduler
    docker volume rm bgbilling-scheduler

    docker rm --force --volumes bgbilling-billing
    docker volume rm bgbilling-billing

    docker rm --force --volumes bgbilling-activemq

    docker rm --force --volumes bgbilling-mysql-slave
    docker volume rm bgbilling-mysql-slave

    docker rm --force --volumes bgbilling-mysql-backup
    docker volume rm bgbilling-mysql-backup

    docker rm --force --volumes bgbilling-mysql-master
    docker volume rm bgbilling-mysql-master

    docker rm --force --volumes bgbilling-grafana

    docker rm --force --volumes bgbilling-graphite-statsd
fi
