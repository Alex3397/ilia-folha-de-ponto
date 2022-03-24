package br.com.ilia.digital.folhadeponto.services.v2;

import br.com.ilia.digital.folhadeponto.objects.Registry;
import br.com.ilia.digital.folhadeponto.repositories.database.RegistryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class V2RegistryService {

    private RegistryRepository registryRepository;

    public Optional<Registry> findRegistryByDate(String date) {
        return registryRepository.findByDia(date);
    }
}
