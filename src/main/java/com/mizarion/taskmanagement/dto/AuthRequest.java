package com.mizarion.taskmanagement.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    @Schema(example = "user@example.com", description = "Email of the user", required = true)
    @JsonAlias(value = {"username", "login", "mail"})
    @NotBlank(message = "Username required")
    private String email;

    @Schema(example = "password", description = "Password of the user", required = true)
    @NotBlank(message = "Password required")
    private String password;
}
