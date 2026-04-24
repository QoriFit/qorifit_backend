FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests \
    -Dmaven.compiler.fork=true \
    -Dmaven.compiler.maxmem=256m \
    -Dmaven.compiler.meminitial=128m \
    -Dmaven.compiler.showWarnings=false \
    -Dmaven.main.skip=false \
    -Dspring-boot.repackage.skip=false \
    -Xmx300m

FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Para la ejecución, dejamos 384MB para el app y el resto para el sistema
ENV JAVA_OPTS="-Xmx384m -Xms256m -XX:+UseSerialGC"

EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]