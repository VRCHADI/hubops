package com.gft.hubops.application.service.cotacao;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CalculadoraFreteService {

    public BigDecimal calcularValor(Double pesoKg) {
        if (pesoKg <= 5) {
            return BigDecimal.valueOf(20.00);
        }

        if (pesoKg <= 10) {
            return BigDecimal.valueOf(35.00);
        }

        return BigDecimal.valueOf(50.00);
    }

    public Integer calcularPrazo(String cepDestino) {
        if (cepDestino.startsWith("01")) {
            return 1;
        }

        if (cepDestino.startsWith("02") || cepDestino.startsWith("03")) {
            return 2;
        }

        return 5;
    }
}