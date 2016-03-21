docker run --rm --link mysql:mysql martin/wait -p 3306
docker run --rm --link activemq:activemq martin/wait -p 61616
docker run --rm --link bgbilling:bgbilling martin/wait -p 8080 -t 300

docker run --name bginetaccess --detach \
  --net host \
  --env APP_NAME=BGInetAccess1 \
  --env APP_ID=31415 \
  --env MODULE_ID=1 \
  --env ROOT_DEVICE_ID=1 \
  --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
  --publish 67:67/udp \
  --publish 1812:1812/udp \
  alexanderfefelov/docker-bginetaccess
