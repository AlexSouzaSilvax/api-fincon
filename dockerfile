FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/fincon-0.0.1-SNAPSHOT.jar fincon.jar
ENTRYPOINT ["java","-jar","/fincon.jar"]