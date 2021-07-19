FROM adoptopenjdk/openjdk11:alpine-jre

WORKDIR /app

COPY target/*.jar app.jar

CMD java -Dserver.port=$PORT $JAVA_OPTS --spring.profiles.active=prod -jar app.jar
