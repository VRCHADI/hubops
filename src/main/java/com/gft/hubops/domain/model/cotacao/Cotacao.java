package com.gft.hubops.domain.model.cotacao;

import com.gft.hubops.domain.model.cliente.Cliente;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cotacoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cotacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 9)
    private String cepOrigem;

    @Column(nullable = false, length = 9)
    private String cepDestino;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorFrete;

    @Column(nullable = false)
    private Integer prazoDias;

    @Column(nullable = false)
    private LocalDateTime criadaEm;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}