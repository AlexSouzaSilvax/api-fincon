package com.fincon.dto;

import jakarta.validation.constraints.NotNull;

public record AuthenticationDTO(@NotNull String username, @NotNull String password) {

}
