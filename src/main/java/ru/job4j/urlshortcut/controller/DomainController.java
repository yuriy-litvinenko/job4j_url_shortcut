package ru.job4j.urlshortcut.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.urlshortcut.dto.DomainNameDTO;
import ru.job4j.urlshortcut.dto.DomainRegDTO;
import ru.job4j.urlshortcut.model.Domain;
import ru.job4j.urlshortcut.service.DomainService;

import javax.validation.Valid;

@RestController
@Data
@Tag(name = "Domain Controller")
public class DomainController {
    private final DomainService service;
    private final ModelMapper modelMapper;

    @PostMapping("/registration")
    @Operation(summary = "Domain registration")
    public ResponseEntity<DomainRegDTO> registration(@Valid @RequestBody DomainNameDTO domainNameDTO) {
        Domain domain = modelMapper.map(domainNameDTO, Domain.class);
        domain = service.save(domain);
        return ResponseEntity.status(HttpStatus.CREATED).body(new DomainRegDTO(true, domain.getLogin(),
                domain.getPassword()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<DomainRegDTO> handle() {
        return ResponseEntity.badRequest().body(new DomainRegDTO());
    }
}
