FROM adoptopenjdk/openjdk11:alpine
EXPOSE 8080
ADD /target/api-votacao-cooperativa-1.0.jar api-votacao-cooperativa-1.0.jar
ENTRYPOINT ["java","-jar","api-votacao-cooperativa-1.0.jar"]