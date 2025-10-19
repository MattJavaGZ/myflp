FROM maven:3.9.4-amazoncorretto-21-debian-bookworm AS maven_build
COPY pom.xml /build/
WORKDIR /build
RUN mvn dependency:go-offline -B
COPY src /build/src
RUN mvn clean package

FROM eclipse-temurin:21-alpine AS application
COPY --from=maven-build /build/target/myflp-*.jar /opt/app.jar
WORKDIR /opt
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
