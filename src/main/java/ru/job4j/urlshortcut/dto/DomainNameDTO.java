package ru.job4j.urlshortcut.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Schema(description = "Domain information")
public class DomainNameDTO {
    @Schema(description = "Domain name")
    @NotBlank(message = "Domain name must be not empty")
    private String site;
}
