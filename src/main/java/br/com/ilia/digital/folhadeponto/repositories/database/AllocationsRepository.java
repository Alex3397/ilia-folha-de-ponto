package br.com.ilia.digital.folhadeponto.repositories.database;

import br.com.ilia.digital.folhadeponto.objects.Allocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Interface de Repositório de Alocações no banco de dados
 * @since 2022-03-24 18:37
 */

@Repository
public interface AllocationsRepository extends JpaRepository<Allocation, Long> {
    Optional<Allocation> findByDia(String dia);
    List<Allocation> findByTempo(String tempo);
    List<Allocation> findByProjeto(String projeto);

    List<Allocation> findAll();
}
