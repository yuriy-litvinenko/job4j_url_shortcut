package ru.job4j.urlshortcut.service;

import lombok.Data;
import org.hashids.Hashids;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.urlshortcut.dto.ConvertStatDTO;
import ru.job4j.urlshortcut.model.UrlConvert;
import ru.job4j.urlshortcut.repository.ConvertRepository;

import java.util.List;
import java.util.Optional;

@Service
@Data
public class UrlConvertService {
    private final ConvertRepository repository;
    private final ModelMapper modelMapper;

    public Optional<UrlConvert> findUrlConvertByUrl(String url) {
        return repository.findByUrl(url);
    }

    public Optional<UrlConvert> findUrlConvertByCode(String code) {
        return repository.findByCode(code);
    }

    public List<ConvertStatDTO> findAll() {
        return repository.findAll().stream().map(ConvertStatDTO::dtoConvert).toList();
    }

    public UrlConvert save(UrlConvert urlConvert) {
        String url = urlConvert.getUrl();
        Optional<UrlConvert> urlConvertFound = findUrlConvertByUrl(url);
        if (urlConvertFound.isEmpty()) {
            urlConvert = repository.save(urlConvert);
            Long id = urlConvert.getId();
            Hashids hashids = new Hashids(url);
            String hash = hashids.encode(id + 1000000000);
            urlConvert.setCode(hash);
            urlConvert = repository.save(urlConvert);
        } else {
            urlConvert = urlConvertFound.get();
        }
        return urlConvert;
    }

    @Transactional
    public void countIncr(UrlConvert urlConvert) {
        repository.countIncr(urlConvert.getId());
    }
}
