package com.gft.hubops.adapters.out.persistence.entrega;

import com.gft.hubops.domain.model.entrega.Entrega;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import java.util.List;

public interface EntregaRepository extends JpaRepository<Entrega, Long> {

    List<Entrega> findByClienteId(Long clienteId);

    Optional<Entrega> findByCotacaoId(Long cotacaoId);



}