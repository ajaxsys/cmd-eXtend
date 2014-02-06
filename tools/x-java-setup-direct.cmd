@echo off

set D=%~dp0..\x-java\java\target\dependency
set P=%~dp0..\x-java\groovy-script\proxy.properties
if not exist "%D%" (
	mkdir "%D%"
)
echo Please confirm your proxy if need.
call n %P%

call wget.cmd http://repo1.maven.org/maven2/commons-codec/commons-codec/1.5/commons-codec-1.5.jar "%D%\commons-codec-1.5.jar"
call wget.cmd http://repo1.maven.org/maven2/org/codehaus/groovy/groovy-all/2.0.0/groovy-all-2.0.0.jar "%D%\groovy-all-2.0.0.jar"

