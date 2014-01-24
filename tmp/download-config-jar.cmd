@echo off
set D=%USERPROFILE%\.m2\repository\commons-codec\commons-codec\1.5
mkdir "%D%"
call download.cmd http://repo1.maven.org/maven2/commons-codec/commons-codec/1.5/commons-codec-1.5.jar "%D%\commons-codec-1.5.jar"

