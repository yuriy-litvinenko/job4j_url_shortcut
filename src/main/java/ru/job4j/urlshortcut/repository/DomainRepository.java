package ru.job4j.urlshortcut.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.urlshortcut.model.Domain;

import java.util.Optional;

@Repository
public interface DomainRepository extends CrudRepository<Domain, Integer> {

    Optional<Domain> findBySite(String site);

    Optional<Domain> findByLogin(String login);
}
