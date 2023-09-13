package ru.job4j.urlshortcut.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DomainAuthDTO {
    @NotBlank(message = "Login must not be empty")
    String login;
    @NotBlank(message = "Password must not be empty")
    String password;
}
