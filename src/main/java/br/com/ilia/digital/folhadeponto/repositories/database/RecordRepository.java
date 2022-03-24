package br.com.ilia.digital.folhadeponto.repositories.database;

import br.com.ilia.digital.folhadeponto.objects.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    Optional<Record> findByMes(String mes);
    List<Record> findAll();
}
