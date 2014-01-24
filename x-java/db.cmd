@echo off
call cmdx-config.cmd

set CLASSPATH=%DERBY_INSTALL%\lib\derby.jar;%DERBY_INSTALL%\lib\derbytools.jar;.
java org.apache.derby.tools.ij