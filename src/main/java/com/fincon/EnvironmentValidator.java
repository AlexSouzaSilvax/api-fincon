package com.fincon;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class EnvironmentValidator {

    private static final List<String> REQUIRED_ENV_VARS = List.of(
            "FINCON_DATABASE_URL",
            "FINCON_DATABASE_USERNAME",
            "FINCON_DATABASE_PASSWORD",
            "FINCON_ENVIO_EMAIL_URL",
            "FINCON_SECRET_KEY_PASSWORD",
            "FINCON_DEPLOY_WEB_URLS");

    @PostConstruct
    public void validateEnvVars() {
        for (String envVar : REQUIRED_ENV_VARS) {
            String value = System.getenv(envVar);
            if (value == null || value.isEmpty()) {
                throw new IllegalStateException(
                        "A variável de ambiente " + envVar + " não está configurada corretamente!");
            }
        }
    }
}