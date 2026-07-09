FROM maven:3.9.16-eclipse-temurin-21 AS build
WORKDIR /Webhooker

# Vai baixar dependencias apenas quando houver alteracao no pom.xml
COPY ./pom.xml pom.xml
RUN mvn dependency:go-offline

COPY ./src src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-noble AS runner
WORKDIR /Webhooker
COPY --from=build /Webhooker/target/*.jar ./webhooker-app.jar
ENTRYPOINT ["java", "-jar", "webhooker-app.jar"]