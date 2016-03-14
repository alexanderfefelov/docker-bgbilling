docker run --name mysql --detach \
  --env MYSQL_ALLOW_EMPTY_PASSWORD=yes \
  --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
  --publish-all \
  mysql:5.5.48
