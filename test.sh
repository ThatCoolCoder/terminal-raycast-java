rm -f target/*.jar && mvn package && java -jar $(find target -name "*-jar-with-dependencies.jar")
