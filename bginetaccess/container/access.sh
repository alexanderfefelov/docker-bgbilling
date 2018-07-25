#!/bin/sh

cd ${0%${0##*/}}.

. ./setenv.sh

APP_HOME=.
JMX="-javaagent:${APP_HOME}/lib/ext/jmx2graphite-1.2.4-agent-min.jar=SERVICE_NAME=bgbilling-access;GRAPHITE_HOSTNAME=graphite.bgbilling.local"
CLASSPATH=$APP_HOME:$APP_HOME/lib/ext/bgcommon-boot.jar:$APP_HOME/lib/ext/*
COMMON_PARAMS="${JMX} -Dnetworkaddress.cache.ttl=3600 -Djava.net.preferIPv4Stack=true -Dboot.info=1 -Dapp.name=BGInetAccess -Djava.endorsed.dirs=${BGBILLING_SERVER_DIR}/lib/endorsed:${JAVA_HOME}/lib/endorsed"
LOG_PARAMS="-Dlog.dir.path=log/ -Dlog4j.configuration=log4j-access.xml"
NAME=inet-access
NAME_SHORT=access
ADMIN_PORT=1951
MEMORY=-Xmx256m

if [ "$1" = "start" ]; then
	nohup  ${JAVA_HOME}/bin/java ${COMMON_PARAMS} ${LOG_PARAMS} ${MEMORY} -Dadmin.port=$ADMIN_PORT -cp ${CLASSPATH} ru.bitel.common.bootstrap.Boot ru.bitel.bgbilling.kernel.application.server.Application ${NAME} > ./log/${NAME_SHORT}.out 2>&1 & echo $! > .run/${NAME_SHORT}.pid &
else
	if [ "$1" = "debug" ]; then
		#starting in debug mode
	    nohup  ${JAVA_HOME}/bin/java ${COMMON_PARAMS} ${LOG_PARAMS} ${MEMORY} -Dadmin.port=$ADMIN_PORT -cp ${CLASSPATH} -enableassertions -Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,address=5589,server=y,suspend=n ru.bitel.common.bootstrap.Boot ru.bitel.bgbilling.kernel.application.server.Application ${NAME} > ./log/${NAME_SHORT}.out 2>&1 & echo $! > .run/${NAME_SHORT}.pid
	else
		#execute command
		${JAVA_HOME}/bin/java ${COMMON_PARAMS} -Dadmin.port=$ADMIN_PORT -cp ${CLASSPATH} ru.bitel.common.bootstrap.Boot ru.bitel.bgbilling.kernel.application.server.Application ${NAME} $1 $2 $3 $4 $5 $6
	fi
fi