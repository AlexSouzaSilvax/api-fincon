FROM eclipse-temurin:17-jdk-alpine
ENV TZ=America/Sao_Paulo
ENV DATABASE_URL=jdbc:postgresql://localhost:5432/your_database 
ENV DATABASE_USERNAME=your_username 
ENV DATABASE_PASSWORD=your_password
VOLUME /tmp
COPY target/fincon-0.0.1-SNAPSHOT.jar fincon.jar
ENTRYPOINT ["java","-Duser.timezone=America/Sao_Paulo","-jar","/fincon.jar"]