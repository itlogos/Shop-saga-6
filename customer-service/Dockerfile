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
# ========== build ==========
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /build

# 1) сначала копируем poms для кеша
COPY pom.xml .
COPY api-gateway/pom.xml api-gateway/
COPY customer-service/pom.xml customer-service/
COPY order-service/pom.xml order-service/
COPY store-service/pom.xml store-service/

# 2) теперь копируем весь исходный код
COPY . .

# 3) собираем ИМЕННО этот модуль + его зависимости
#    -pl customer-service   → «собери customer-service»
#    -am                    → «дотяни зависимости из соседних модулей»
#    clean package          → «пересобери заново»
# ВАЖНО: в pom должен быть spring-boot-maven-plugin с goal repackage
RUN mvn -q -DskipTests -pl customer-service -am clean package

# ========== runtime ==========
FROM eclipse-temurin:21-jre
WORKDIR /app

# Копируем boot-jar именно из ПАПКИ МОДУЛЯ:
COPY --from=build /build/customer-service/target/*.jar /app/app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]
