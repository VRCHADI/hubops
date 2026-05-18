package com.gft.hubops.domain.model.operacao;

import com.gft.hubops.domain.model.entrega.Entrega;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "operacoes_logisticas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperacaoLogistica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String etapaAtual;

    @Column(nullable = false, length = 40)
    private String statusOperacional;

    @Column(nullable = false)
    private LocalDateTime atualizadaEm;

    @OneToOne
    @JoinColumn(name = "entrega_id")
    private Entrega entrega;
}