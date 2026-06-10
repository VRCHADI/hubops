package com.gft.hubops.domain.model.notificacao;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificacoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(nullable = false, length = 255)
    private String mensagem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private TipoNotificacao tipo;

    @Column(nullable = false)
    private Boolean lida;

    @Column(nullable = false)
    private LocalDateTime criadaEm;

    @Column(nullable = false)
    private Long entregaId;

    @Column(nullable = false)
    private Long clienteId;

    @Column(nullable = false, length = 40)
    private String codigoRastreamento;
}