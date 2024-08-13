# Estágio de construção
FROM ubuntu:latest AS build

RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven

COPY . .

RUN mvn clean install

# Estágio de execução
FROM openjdk:17-jdk-slim

EXPOSE 8080

# Copiar o JAR gerado no estágio de construção para o estágio final
COPY --from=build /target/deploy_render-1.0.0.jar colegiogeneration.jar

# Definir o ponto de entrada correto
ENTRYPOINT ["java", "-jar", "colegiogeneration.jar"]
