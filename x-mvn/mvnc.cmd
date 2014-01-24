@echo off

REM clear
SET archetypeArtifactId=
if not [] == [%1] (
	SET archetypeArtifactId=-DarchetypeArtifactId^=%1
) else (
	Echo Create a maven project from list.
)

Echo ******** Sample ********
Echo mvn archetype:generate \
Echo   -DarchetypeGroupId=org.apache.maven.archetypes \
Echo   -DgroupId=com.mycompany.app \
Echo   -DartifactId=my-app \
Echo   -Dversion=1.0 \
if "" == "%archetypeArtifactId%" (
	Echo   -DartifactId=[select from list] **See help: man mvnc
) else (
	Echo   %archetypeArtifactId%
)
Echo ******** input ********
Echo;
set /p DgroupId=Enter GroupId:
set /p DartifactId=Enter ArtifactId:

mvn archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -Dversion=1.0 -DgroupId=%DgroupId% -DartifactId=%DartifactId% %archetypeArtifactId%
