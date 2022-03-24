package br.com.ilia.digital.folhadeponto.services.v2;

import br.com.ilia.digital.folhadeponto.objects.Registry;
import br.com.ilia.digital.folhadeponto.repositories.database.RegistryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Classe de serviço de Registros
 * @version 2
 * @since 2022-03-23 17:27
 */

@Service
@AllArgsConstructor
public class V2RegistryService {

    private RegistryRepository registryRepository;

    /**
     * Função para encontrar registro com base na data
     * @since 2022-03-23 17:27
     */
    public Optional<Registry> findRegistryByDate(String date) {
        return registryRepository.findByDia(date);
    }

    /**
     * Função para buscar todos os registros salvos
     * @since 2022-03-23 17:27
     */
    public List<Registry> findAll() {
        return registryRepository.findAll();
    }
}
