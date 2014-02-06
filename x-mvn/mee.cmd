REM  -DdownloadSources -DdownloadJavadocs
call mvn eclipse:eclipse -Declipse.useProjectReferences=false -Dmaven.test.skip=true %*