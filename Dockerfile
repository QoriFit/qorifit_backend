
FROM amazoncorretto:21-alpine AS build

RUN apk add --no-cache maven

WORKDIR /app
COPY . .

RUN MAVEN_OPTS="-Xmx300m" mvn clean package -DskipTests \
    -Dmaven.test.skip=true \
    -Dmaven.compiler.fork=true \
    -Dmaven.compiler.maxmem=256m \
    -DshowWarnings=false

FROM amazoncorretto:21-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ENV JAVA_OPTS="-Xmx384m -Xms256m -XX:+UseSerialGC -XX:+ExitOnOutOfMemoryError"

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]