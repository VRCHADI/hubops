package com.gft.hubops.adapters.out.persistence.cliente;

import com.gft.hubops.domain.model.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByEmail(String email);

    Optional<Cliente> findByDocumento(String documento);
}