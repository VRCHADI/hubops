package com.gft.hubops.domain.model.entrega;

import com.gft.hubops.domain.model.cotacao.Cotacao;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "entregas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private String codigoRastreamento;

    @Column(nullable = false, length = 40)
    private String status;

    @Column(nullable = false)
    private LocalDateTime criadaEm;

    @OneToOne
    @JoinColumn(name = "cotacao_id")
    private Cotacao cotacao;
}