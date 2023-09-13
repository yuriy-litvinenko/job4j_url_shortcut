package ru.job4j.urlshortcut.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Short code")
public class ConvertCodeDTO {
    @Schema(description = "Code")
    String code;
}
