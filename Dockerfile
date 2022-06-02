FROM maven:3.8.5-openjdk-18
COPY src /src
COPY pom.xml /
RUN mvn clean test