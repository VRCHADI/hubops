package com.gft.hubops.adapters.out.persistence.entrega;

import com.gft.hubops.adapters.in.web.entrega.dto.EntregaPorStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EntregaJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<EntregaPorStatusResponse> contarEntregasPorStatus() {

        String sql = """
                SELECT
                    status,
                    COUNT(*) AS quantidade
                FROM entregas
                GROUP BY status
                ORDER BY status
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new EntregaPorStatusResponse(
                        rs.getString("status"),
                        rs.getLong("quantidade")
                )
        );
    }
}