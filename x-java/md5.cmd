@echo off
REM Call /? for detail
REM d:driver p:path f:filename&extion n:fileName x:fileExtention
set anyFileOrString=%~dpf1
pushd "%~dp0\java\src\encrypt"
call jsh MD5.java %anyFileOrString%
popd
