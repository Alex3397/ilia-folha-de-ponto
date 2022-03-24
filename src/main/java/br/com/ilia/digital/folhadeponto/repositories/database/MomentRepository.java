package br.com.ilia.digital.folhadeponto.repositories.database;

import br.com.ilia.digital.folhadeponto.objects.Moment;
import lombok.SneakyThrows;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Interface de Repositório de batidas de ponto no banco de dados
 * @since 2022-03-23 17:27
 */

@Repository
public interface MomentRepository extends JpaRepository<Moment, Long> {

    Optional<Moment> findByDataHora(String dataHora);
    List<Moment> findAll();
}
