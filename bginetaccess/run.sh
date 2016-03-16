docker run --rm --link bgbilling:bgbilling martin/wait -p 8080 -t 300

docker run --name bginetaccess --detach \
  --env APP_NAME=BGInetAccess \
  --env APP_ID=31415 \
  --env MODULE_ID=1 \
  --env ROOT_DEVICE_ID=1 \
  --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
  --link mysql:mysql \
  --link activemq:activemq \
  --publish 1812:1812/udp \
  --publish 10067:10067/udp \
  alexanderfefelov/docker-bginetaccess
