#!/bin/sh
APPDIR=$(dirname ${0} | sed -e "s|^\([^/]\)|`pwd`/\1|g")

APPJAR=${APPDIR}/tcpswitch.jar
APPLOG=${APPDIR}/tcpswitch.log
ERRLOG=${APPDIR}/tcpswitch.err
APPPID=${APPDIR}/tcpswitch.pid

cd ${APPDIR}
nohup /usr/bin/java -jar ${APPJAR} -daemon $@ >>${APPLOG} 2>>${ERRLOG} &
echo $! >${APPPID}
