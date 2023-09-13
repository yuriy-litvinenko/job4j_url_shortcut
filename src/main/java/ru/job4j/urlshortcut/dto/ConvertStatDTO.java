package ru.job4j.urlshortcut.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.job4j.urlshortcut.model.UrlConvert;

@Data
@Schema(description = "Number of requests statistics")
public class ConvertStatDTO {
    @Schema(description = "Url")
    private String url;
    @Schema(description = "Number of requests")
    private int total;

    public static ConvertStatDTO dtoConvert(UrlConvert urlConvert) {
        ConvertStatDTO convertStatDTO = new ConvertStatDTO();
        convertStatDTO.setUrl(urlConvert.getUrl());
        convertStatDTO.setTotal(urlConvert.getCount());
        return convertStatDTO;
    }
}
