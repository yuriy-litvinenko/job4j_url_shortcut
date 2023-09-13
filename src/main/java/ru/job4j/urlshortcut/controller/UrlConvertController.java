package ru.job4j.urlshortcut.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.urlshortcut.dto.ConvertCodeDTO;
import ru.job4j.urlshortcut.dto.ConvertStatDTO;
import ru.job4j.urlshortcut.dto.ConvertUrlDTO;
import ru.job4j.urlshortcut.model.UrlConvert;
import ru.job4j.urlshortcut.service.UrlConvertService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@Data
@Tag(name = "UrlConvert Controller")
public class UrlConvertController {
    private final UrlConvertService service;
    private final ModelMapper modelMapper;

    @PostMapping("/convert")
    @Operation(summary = "Convert long url to short code")
    public ResponseEntity<ConvertCodeDTO> convert(@Valid @RequestBody ConvertUrlDTO convertUrlDTO) {
        UrlConvert urlConvert = modelMapper.map(convertUrlDTO, UrlConvert.class);
        ConvertCodeDTO convertCodeDTO = modelMapper.map(service.save(urlConvert), ConvertCodeDTO.class);
        return new ResponseEntity<>(convertCodeDTO, HttpStatus.OK);
    }

    @GetMapping("/redirect/{code}")
    @Operation(summary = "Redirect to the source url by a short code")
    public ResponseEntity<Void> redirect(@PathVariable String code) {
        Optional<UrlConvert> urlConvert = service.findUrlConvertByCode(code);
        if (urlConvert.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.countIncr(urlConvert.get());
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(urlConvert.get().getUrl())).build();
    }

    @GetMapping("/statistic")
    @Operation(summary = "Get statistics on the number of requests")
    public List<ConvertStatDTO> statistic() {
        return service.findAll();
    }
}
