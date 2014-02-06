call mvn clean package
call mvn dependency:copy-dependencies

move target\cmdx.jar .
move target\dependency .