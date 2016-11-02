#!/bin/sh
APPDIR=$(dirname ${0} | sed -e "s|^\([^/]\)|`pwd`/\1|g")

APPPID=${APPDIR}/tcpswitch.pid

p=`cat ${APPPID} 2>/dev/null`
test -n "$p" && (
        echo "stopping ${p}"
        kill $p
        rm -f ${APPPID}
)
