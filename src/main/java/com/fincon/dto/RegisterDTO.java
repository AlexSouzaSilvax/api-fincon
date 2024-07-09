package com.fincon.dto;

import com.fincon.enums.UserRole;
import jakarta.validation.constraints.NotNull;

public record RegisterDTO(@NotNull String login,@NotNull String senha, @NotNull UserRole role ) {
    
}
