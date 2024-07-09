package com.fincon.dto;

import com.fincon.enums.UserRole;
import jakarta.validation.constraints.NotNull;

public record RegisterDTO(@NotNull String username,@NotNull String password, @NotNull UserRole role ) {
    
}
