#!/usr/bin/env bash

read -p "All images will be removed. Are you sure? " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]
then
    docker rmi alexanderfefelov/bgbilling-access
    docker rmi alexanderfefelov/bgbilling-accounting
    docker rmi alexanderfefelov/bgbilling-activemq-1
    docker rmi alexanderfefelov/bgbilling-activemq-2
    docker rmi alexanderfefelov/bgbilling-activemq-3
    docker rmi alexanderfefelov/bgbilling-base
    docker rmi alexanderfefelov/bgbilling-billing
    docker rmi alexanderfefelov/bgbilling-chronograf
    docker rmi alexanderfefelov/bgbilling-dnsmasq
    docker rmi alexanderfefelov/bgbilling-elasticsearch
    docker rmi alexanderfefelov/bgbilling-grafana
    docker rmi alexanderfefelov/bgbilling-influxdb
    docker rmi alexanderfefelov/bgbilling-kapacitor
    docker rmi alexanderfefelov/bgbilling-kibana
    docker rmi alexanderfefelov/bgbilling-logstash
    docker rmi alexanderfefelov/bgbilling-mysql
    docker rmi alexanderfefelov/bgbilling-ofelia
    docker rmi alexanderfefelov/bgbilling-populator
    docker rmi alexanderfefelov/bgbilling-scheduler
    docker rmi alexanderfefelov/bgbilling-telegraf
fi
