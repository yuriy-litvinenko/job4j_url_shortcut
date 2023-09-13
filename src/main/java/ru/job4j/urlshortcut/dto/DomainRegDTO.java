package ru.job4j.urlshortcut.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Authorization data")
public class DomainRegDTO {
    @Schema(description = "Registration status")
    boolean registration;
    @Schema(description = "Login")
    String login;
    @Schema(description = "Password")
    String password;
}
