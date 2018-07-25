#!/bin/sh

cd ${0%${0##*/}}.

. ./setenv.sh

JMX="-javaagent:${BGBILLING_SERVER_DIR}/lib/ext/jmx2graphite-1.2.4-javaagent.jar=SERVICE_NAME=bgbilling-billing;GRAPHITE_HOSTNAME=graphite.bgbilling.local"
CLASSPATH=${BGBILLING_SERVER_DIR}:${BGBILLING_SERVER_DIR}/lib/ext/bgcommon-boot.jar:${BGBILLING_SERVER_DIR}/lib/ext/*
COMMON_PARAMS="${JMX} -Dnetworkaddress.cache.ttl=3600 -Djava.net.preferIPv4Stack=true -Djava.endorsed.dirs=${BGBILLING_SERVER_DIR}/lib/endorsed:${JAVA_HOME}/lib/endorsed"
PARAMS="-Dboot.info=1 -Dapp.name=BGBillingServer -Dlog4j.configuration=data/log4j.xml -Dlog.dir.path=log/ -Dlog.prefix=server"
MEMORY="-Xmx512m"


if [ ! -d "${BGBILLING_SERVER_DIR}/.run" ] ; then
    mkdir ${BGBILLING_SERVER_DIR}/.run
fi

if [ "$1" = "start" ]; then
	#starting
    nohup  ${JAVA_HOME}/bin/java ${COMMON_PARAMS} ${PARAMS} ${MEMORY} -cp ${CLASSPATH} ru.bitel.common.bootstrap.Boot bitel.billing.server.Server $1 $2 $3 > ./log/server.out 2>&1 & echo $! > .run/bgbilling.pid &
else
	if [ "$1" = "debug" ]; then
		#starting in debug mode
	    nohup  ${JAVA_HOME}/bin/java ${COMMON_PARAMS} ${PARAMS} ${MEMORY} -cp ${CLASSPATH} -enableassertions -Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,address=5589,server=y,suspend=n  ru.bitel.common.bootstrap.Boot bitel.billing.server.Server start $2 $3 > ./log/server.out 2>&1 & echo $! > .run/bgbilling.pid &
	else
		#execute command
		${JAVA_HOME}/bin/java ${COMMON_PARAMS} -cp ${CLASSPATH} ru.bitel.common.bootstrap.Boot bitel.billing.server.Server $1 $2 $3
	fi
fi
