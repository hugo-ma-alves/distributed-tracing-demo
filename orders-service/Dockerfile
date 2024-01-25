FROM maven:3.9.6-eclipse-temurin-21 as dependencies

COPY pom.xml .

RUN mvn --batch-mode dependency:go-offline dependency:resolve-plugins

FROM maven:3.9.6-eclipse-temurin-21 AS build

COPY --from=dependencies /root/.m2 /root/.m2

COPY src /home/app/src
COPY pom.xml /home/app

RUN mvn -f /home/app/pom.xml clean package

FROM eclipse-temurin:21-jre-jammy

EXPOSE 8080

COPY --from=docker.elastic.co/observability/apm-agent-java:latest /usr/agent/elastic-apm-agent.jar /elastic-apm-agent.jar

COPY --from=build /home/app/target/*.jar /app/orders-service.jar

ENTRYPOINT ["java", "-javaagent:/elastic-apm-agent.jar",\
"-Delastic.apm.service_name=${ELASTIC_APM_SERVICE_NAME}",\
"-Delastic.apm.application_packages=com.hugomalves",\
"-Delastic.apm.server_urls=${ELASTIC_APM_SERVER_URL}",\
 "-Djava.security.egd=file:/dev/./urandom",\
 "-jar", "/app/orders-service.jar"]
