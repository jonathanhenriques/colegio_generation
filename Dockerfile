
# Etapa de build
FROM maven:3.8.7-eclipse-temurin-17 AS build

# Copia o código-fonte e arquivos de configuração do projeto para o contêiner
COPY . /app
WORKDIR /app

# Compila o projeto e gera o JAR
RUN mvn clean package -DskipTests

# Etapa de execução
FROM eclipse-temurin:17-jdk-jammy

# Exponha a porta 8080
EXPOSE 8080

# Copia o JAR gerado na etapa de build para a etapa de execução
COPY --from=build /app/target/colegiogeneration-0.0.1-SNAPSHOT.jar app.jar

# Define o ponto de entrada para o contêiner
ENTRYPOINT ["java", "-jar", "app.jar"]


