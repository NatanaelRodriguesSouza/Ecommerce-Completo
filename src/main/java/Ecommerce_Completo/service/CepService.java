package Ecommerce_Completo.service;

import Ecommerce_Completo.model.DTO.CepDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CepService {

    private final WebClient viacepWebClient;

    public CepService(WebClient viacepWebClient) {
        this.viacepWebClient = viacepWebClient;
    }

    public CepDTO buscarCep(String cep) {
        String cepLimpo = cep.replaceAll("\\D", "");

        if (cepLimpo.length() != 8) {
            throw new IllegalArgumentException("CEP deve ter 8 dígitos.");
        }

        CepDTO dto = viacepWebClient.get()
                .uri("/ws/{cep}/json/", cepLimpo)
                .retrieve()
                .bodyToMono(CepDTO.class)
                .block();

        if (dto == null || Boolean.TRUE.equals(dto.getErro())) {
            throw new RuntimeException("CEP não encontrado.");
        }

        return dto;
    }
}