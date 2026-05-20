package com.gft.hubops.adapters.out.persistence.endereco;

import com.gft.hubops.domain.model.endereco.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    List<Endereco> findByClienteId(Long clienteId);
}