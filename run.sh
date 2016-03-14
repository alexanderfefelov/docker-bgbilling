docker run --name activemq --detach \
  --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
  --publish-all \
  rmohr/activemq:5.13.1

docker run --name bgbilling --detach \
  --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
  --link activemq:activemq \
  --publish 8080:8080 \
  alexanderfefelov/docker-bgbilling
