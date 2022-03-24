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
 * Interface de Repositório de Registros no banco de dados
 * @since 2022-03-23 17:27
 */

@Repository
public interface RegistryRepository extends JpaRepository<Registry, Long> {

    Optional<Registry> findByDia(String dia);
    List<Registry> findAll();
}
