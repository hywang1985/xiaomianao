set MAVEN_OPTS= -Xms128m -Xmx512m
mvn clean package -Pprod -Dmaven.test.skip=true && pause