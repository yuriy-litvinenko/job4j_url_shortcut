package ru.job4j.urlshortcut.service;

import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.model.Domain;
import ru.job4j.urlshortcut.repository.DomainRepository;

import java.util.Optional;

@Service
@Data
public class DomainService {
    private final DomainRepository repository;
    private final BCryptPasswordEncoder encoder;

    public Domain save(Domain domain) {
        String login = RandomStringUtils.randomAlphabetic(8);
        String passChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789$&@?<>~!%#";
        String password = RandomStringUtils.random(12, passChars);
        domain.setLogin(login);
        domain.setPassword(encoder.encode(password));
        domain = repository.save(domain);
        domain.setPassword(password);
        return domain;
    }

    public Optional<Domain> findDomainByLogin(String login) {
        return repository.findByLogin(login);
    }
}
