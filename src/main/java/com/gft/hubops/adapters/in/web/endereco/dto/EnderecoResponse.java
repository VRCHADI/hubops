package com.gft.hubops.adapters.in.web.endereco.dto;

public record EnderecoResponse(
        Long id,
        String cep,
        String rua,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String estado,
        Long clienteId
) {
}