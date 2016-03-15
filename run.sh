docker run --name mysql --detach \
  --env MYSQL_ALLOW_EMPTY_PASSWORD=yes \
  --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
  --publish-all \
  mysql:5.5.48

docker run --rm --link mysql:mysql martin/wait -p 3306

docker run --name activemq --detach \
  --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
  --publish-all \
  rmohr/activemq:5.13.1

docker run --rm --link activemq:activemq martin/wait -p 61616

docker run --name bgbilling --detach \
  --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
  --link mysql:mysql \
  --link activemq:activemq \
  --publish 8080:8080 \
  alexanderfefelov/docker-bgbilling
