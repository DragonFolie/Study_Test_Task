
FROM maven:3.6.3-jdk-11-slim

COPY ["target/demo-0.0.1-SNAPSHOT.jar","demo-0.0.1-SNAPSHOT.jar"]

ENTRYPOINT ["java","-jar","demo-0.0.1-SNAPSHOT.jar"]



