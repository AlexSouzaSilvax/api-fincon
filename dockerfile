FROM eclipse-temurin:17-jdk-alpine
ENV TZ=America/Sao_Paulo
VOLUME /tmp
COPY target/fincon-0.0.1-SNAPSHOT.jar fincon.jar
ENTRYPOINT ["java","-Duser.timezone=America/Sao_Paulo","-jar","/fincon.jar"]