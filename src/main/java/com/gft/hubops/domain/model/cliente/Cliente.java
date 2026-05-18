package com.gft.hubops.domain.model.cliente;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.gft.hubops.domain.model.endereco.Endereco;

import java.util.List;

import com.gft.hubops.domain.model.cotacao.Cotacao;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Column(nullable = false, unique = true, length = 14)
    private String documento;

    @Column(nullable = false)
    private LocalDateTime criadoEm;

    @OneToMany(mappedBy = "cliente")
    private List<Endereco> enderecos;

    @OneToMany(mappedBy = "cliente")
    private List<Cotacao> cotacoes;
}