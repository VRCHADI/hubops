package com.gft.hubops.adapters.out.persistence.notificacao;

import com.gft.hubops.domain.model.notificacao.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {

    List<Notificacao> findAllByOrderByCriadaEmDesc();

    long countByLidaFalse();
}