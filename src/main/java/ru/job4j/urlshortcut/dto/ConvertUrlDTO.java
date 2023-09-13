package ru.job4j.urlshortcut.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Schema(description = "Source url")
public class ConvertUrlDTO {
    @NotBlank(message = "Url must not be empty")
    @Schema(description = "Url")
    String url;
}
