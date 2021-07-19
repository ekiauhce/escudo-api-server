FROM adoptopenjdk/openjdk11:alpine-jre

WORKDIR /app

COPY target/*.jar app.jar

CMD java -Dserver.port=$PORT $JAVA_OPTS -jar app.jar --spring.profiles.active=prod
