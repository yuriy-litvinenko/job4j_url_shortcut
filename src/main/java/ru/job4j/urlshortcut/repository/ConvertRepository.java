package ru.job4j.urlshortcut.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.job4j.urlshortcut.model.UrlConvert;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConvertRepository extends CrudRepository<UrlConvert, Integer> {

    Optional<UrlConvert> findByUrl(String url);

    Optional<UrlConvert> findByCode(String code);

    List<UrlConvert> findAll();

    @Modifying
    @Query("update UrlConvert t set t.count = t.count + 1 where t.id = :id")
    void countIncr(@Param("id") Long id);
}
