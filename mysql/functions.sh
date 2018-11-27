TIMEOUT=600

run_master() {
    docker run \
      --name $1 \
      --hostname $2 \
      --detach \
      --env SERVER_ID=$3 \
      --env MODE=master \
      --env MYSQL_ROOT_PASSWORD=password \
      --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
      --volume $1:/var/lib/mysql \
      --publish 3306:3306 \
      alexanderfefelov/bgbilling-mysql \
    && docker run --rm --link $1:foobar martin/wait -t $TIMEOUT
}

run_slave() {
    docker run \
      --name $1 \
      --hostname $2 \
      --detach \
      --env SERVER_ID=$3 \
      --env MODE=slave \
      --env MYSQL_ROOT_PASSWORD=password \
      --env MASTER_HOST=master.mysql.bgbilling.local \
      --volume /etc/localtime:/etc/localtime:ro --volume /etc/timezone:/etc/timezone:ro \
      --volume $1:/var/lib/mysql \
      --publish 1000$3:3306 \
      alexanderfefelov/bgbilling-mysql \
    && docker run --rm --link $1:foobar martin/wait -t $TIMEOUT \
    && docker exec $1 cp /read-only.cnf /etc/mysql/mysql.conf.d/ \
    && docker restart $1 \
    && docker run --rm --link $1:foobar martin/wait -t $TIMEOUT
}
