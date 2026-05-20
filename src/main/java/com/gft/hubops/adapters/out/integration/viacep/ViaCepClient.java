package com.gft.hubops.adapters.out.integration.viacep;

import com.gft.hubops.adapters.out.integration.viacep.dto.ViaCepResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ViaCepClient {

    private final RestClient restClient;

    public ViaCepClient() {
        this.restClient = RestClient.builder()
                .baseUrl("https://viacep.com.br/ws")
                .build();
    }

    public ViaCepResponse buscarCep(String cep) {

        return restClient.get()
                .uri("/{cep}/json/", cep)
                .retrieve()
                .body(ViaCepResponse.class);
    }
}