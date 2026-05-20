package com.gft.hubops.adapters.out.integration.viacep.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ViaCepResponse(

        String cep,

        @JsonProperty("logradouro")
        String rua,

        String complemento,

        String bairro,

        @JsonProperty("localidade")
        String cidade,

        @JsonProperty("uf")
        String estado,

        Boolean erro
) {
}