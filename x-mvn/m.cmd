set MAVEN_OPTS="-Xmx512m"
mvn clean package -Plocal -Dmaven.test.skip=true %*