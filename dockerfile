FROM eclipse-temurin:17-jdk-alpine
ENV TZ=America/Sao_Paulo
ENV FINCON_DATABASE_URL=jdbc:postgresql://localhost:5432/your_database
ENV FINCON_DATABASE_USERNAME=your_username
ENV FINCON_DATABASE_PASSWORD=your_password
ENV FINCON_ENVIO_EMAIL_URL=url_api_envio_email
ENV FINCON_SECRET_KEY_PASSWORD=secret_key_password
VOLUME /tmp
COPY target/fincon-0.0.1-SNAPSHOT.jar fincon.jar
ENTRYPOINT ["java","-Duser.timezone=America/Sao_Paulo","-jar","/fincon.jar"]