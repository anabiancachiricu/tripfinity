# docker build -t backendservice ./tripfinity

FROM maven:3.8.4 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package

FROM openjdk:17
WORKDIR /app
COPY --from=build /app/target/*.jar /app/*.jar
ENTRYPOINT ["java", "-jar", "/app/*.jar"]