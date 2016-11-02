@ECHO OFF
SETLOCAL
SET PATHLIB = %CD%

%PATHLIB\jrew\bin\java -Xmx1024m -jar %PATHLIB\Server\ComSpyClient.jar -classpath %PATHLIB\dist\lib\commons-net-3.0.1.jar:%PATHLIB\dist\lib\httpclient-4.3.6.jar:%PATHLIB\dist\lib\httpcore-4.3.3.jar:%PATHLIB\dist\lib\httpmime-4.3.6.jar:%PATHLIB\dist\lib\javaFlacEncoder-0.3.1.jar:%PATHLIB\dist\lib\java-json.jar:%PATHLIB\dist\lib\jmf.jar:%PATHLIB\dist\lib\jna-4.1.0.jar:%PATHLIB\dist\lib\jna-platform-4.1.0.jar:%PATHLIB\dist\lib\KeyboardHook.jar:%PATHLIB\dist\lib\libLiveAudioRemotle.jar:%PATHLIB\dist\lib\libScreenMonitor.jar:%PATHLIB\dist\lib\org-apache-commons-logging.jar:%PATHLIB\dist\lib\synthetica.jar:%PATHLIB\dist\lib\syntheticaClassy.jar:%PATHLIB\dist\lib\jarinjarloader.zip
