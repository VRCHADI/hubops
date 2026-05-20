package com.gft.hubops.adapters.out.persistence.cotacao;

import com.gft.hubops.domain.model.cotacao.Cotacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CotacaoRepository extends JpaRepository<Cotacao, Long> {

    List<Cotacao> findByClienteId(Long clienteId);
}