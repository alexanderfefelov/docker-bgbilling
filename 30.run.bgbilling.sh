docker run --name bgbilling --detach \
  --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
  --link mysql:mysql \
  --link activemq:activemq \
  --publish 8080:8080 \
  alexanderfefelov/docker-bgbilling
