#!/usr/bin/env bash

read -p "All containers and volumes will be removed. Are you sure? " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]
then
    docker rm --force --volumes bgbilling-access
    docker volume rm bgbilling-access

    docker rm --force --volumes bgbilling-accounting
    docker volume rm bgbilling-accounting

    docker rm --force --volumes bgbilling-activemq-1
    docker rm --force --volumes bgbilling-activemq-2
    docker rm --force --volumes bgbilling-activemq-3

    docker rm --force --volumes bgbilling-billing
    docker volume rm bgbilling-billing

    docker rm --force --volumes bgbilling-chronograf
    docker volume rm bgbilling-chronograf-data
    docker volume rm bgbilling-chronograf-share

    docker rm --force --volumes bgbilling-elasticsearch
    docker volume rm bgbilling-elasticsearch-conf
    docker volume rm bgbilling-elasticsearch-data

    docker rm --force --volumes bgbilling-grafana

    docker rm --force --volumes bgbilling-graphite-statsd

    docker rm --force --volumes bgbilling-influxdb
    docker volume rm bgbilling-influxdb-conf
    docker volume rm bgbilling-influxdb-data

    docker rm --force --volumes bgbilling-kapacitor
    docker volume rm bgbilling-kapacitor-conf
    docker volume rm bgbilling-kapacitor-data

    docker rm --force --volumes bgbilling-mysql-backup
    docker volume rm bgbilling-mysql-backup

    docker rm --force --volumes bgbilling-mysql-master
    docker volume rm bgbilling-mysql-master

    docker rm --force --volumes bgbilling-mysql-slave
    docker volume rm bgbilling-mysql-slave

    docker rm --force --volumes bgbilling-ofelia
    docker volume rm bgbilling-ofelia

    docker rm --force --volumes bgbilling-redis

    docker rm --force --volumes bgbilling-scheduler
    docker volume rm bgbilling-scheduler

    docker rm --force --volumes bgbilling-telegraf
    docker volume rm bgbilling-telegraf-conf

    docker rm --force --volumes bgbilling-vault
    docker volume rm bgbilling-vault-file
    docker volume rm bgbilling-vault-log
fi
