#!/bin/bash

read -p "All garbage will be collected. Are you sure? " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]
then
  docker run --rm --volume /var/run/docker.sock:/var/run/docker.sock --volume /etc:/etc spotify/docker-gc
fi
