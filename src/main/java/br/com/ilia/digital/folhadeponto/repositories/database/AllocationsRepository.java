package br.com.ilia.digital.folhadeponto.repositories.database;

import br.com.ilia.digital.folhadeponto.objects.Allocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AllocationsRepository extends JpaRepository<Allocation, Long> {
    Optional<Allocation> findByDia(String dia);
    Optional<Allocation> findByTempo(String tempo);
    Optional<Allocation> findByProjeto(String projeto);

    List<Allocation> findAll();
}
