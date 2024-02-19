FROM openjdk:17.0.1-jdk-slim
ADD /target/Watch2gether-0.0.1-SNAPSHOT.jar W2G.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","W2G.jar"]