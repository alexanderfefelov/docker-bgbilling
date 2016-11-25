#!/bin/bash

read -p "All orphaned Docker volumes will be deleted. Are you sure? " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]
then
  docker run --rm --volume /var/run/docker.sock:/var/run/docker.sock --volume $(readlink -f /var/lib/docker):/var/lib/docker martin/docker-cleanup-volumes
fi
