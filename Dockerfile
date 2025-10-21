# ---- Build ---------------------------------------------------------
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -q -e -DskipTests package -Dquarkus.package.type=uber-jar

# ---- Runtime -------------------------------------------------------
FROM quay.io/quarkus/ubi9-quarkus-micro-image:2.0
WORKDIR /work/
RUN chown 1001 /work && chmod "g+rwX" /work && chown 1001:root /work
COPY --from=build --chown=1001:root --chmod=0755 /workspace/target/*-runner /work/application

# Porta do Render
ENV QUARKUS_HTTP_PORT=${PORT}
EXPOSE 8080
USER 1001
ENTRYPOINT ["./application","-Dquarkus.http.host=0.0.0.0","-Dquarkus.http.port=${PORT}"]
