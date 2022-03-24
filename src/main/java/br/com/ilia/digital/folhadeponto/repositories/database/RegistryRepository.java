package br.com.ilia.digital.folhadeponto.repositories.database;

import br.com.ilia.digital.folhadeponto.objects.Registry;
import lombok.SneakyThrows;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para Registro de horas de um Empregado
 *
 * @since 2022-03-23 17:27
 */

@Repository
public interface RegistryRepository extends JpaRepository<Registry, Long> {

    Optional<Registry> findByDia(String dia);
    List<Registry> findAll();
}
