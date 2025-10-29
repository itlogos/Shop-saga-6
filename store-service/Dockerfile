## syntax=docker/dockerfile:1
#FROM eclipse-temurin:21-jdk AS build
#WORKDIR /app
#COPY mvnw* pom.xml ./
#COPY .mvn .mvn
#RUN ./mvnw -q -DskipTests dependency:go-offline || true
#COPY src src
#RUN ./mvnw -q -DskipTests package
#
#FROM eclipse-temurin:21-jre
#WORKDIR /app
#COPY --from=build /app/target/*.jar app.jar
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","/app/app.jar"]
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /build

COPY pom.xml .
COPY api-gateway/pom.xml api-gateway/
COPY customer-service/pom.xml customer-service/
COPY order-service/pom.xml order-service/
COPY store-service/pom.xml store-service/
COPY . .

RUN mvn -q -DskipTests -pl store-service -am clean package

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /build/store-service/target/*.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
