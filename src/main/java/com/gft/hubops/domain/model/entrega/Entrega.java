package com.gft.hubops.domain.model.entrega;

import com.gft.hubops.domain.model.cotacao.Cotacao;
import jakarta.persistence.*;
import lombok.*;
import com.gft.hubops.domain.model.cliente.Cliente;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusEntrega status;

    @Column(nullable = false)
    private LocalDateTime criadaEm;

    @OneToOne
    @JoinColumn(name = "cotacao_id")
    private Cotacao cotacao;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

}