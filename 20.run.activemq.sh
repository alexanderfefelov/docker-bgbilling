docker run --name activemq --detach \
  --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
  --publish-all \
  rmohr/activemq:5.13.1
