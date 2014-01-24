@ECHO OFF
REM mvn jetspeed:mvn -Dtarget=all

REM install,db,deploy-portal,deploy-pa
@ECHO ON
mvn org.apache.portals.jetspeed-2:jetspeed-mvn-maven-plugin:mvn -Dtarget=deploy-portal

