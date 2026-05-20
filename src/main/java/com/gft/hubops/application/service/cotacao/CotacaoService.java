package com.gft.hubops.application.service.cotacao;

import com.gft.hubops.adapters.in.web.cotacao.dto.CotacaoRequest;
import com.gft.hubops.adapters.in.web.cotacao.dto.CotacaoResponse;
import com.gft.hubops.adapters.out.persistence.cliente.ClienteRepository;
import com.gft.hubops.adapters.out.persistence.cotacao.CotacaoRepository;
import com.gft.hubops.domain.exception.RecursoNaoEncontradoException;
import com.gft.hubops.domain.model.cliente.Cliente;
import com.gft.hubops.domain.model.cotacao.Cotacao;
import com.gft.hubops.domain.model.cotacao.StatusCotacao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CotacaoService {

    private final CotacaoRepository cotacaoRepository;
    private final ClienteRepository clienteRepository;
    private final CalculadoraFreteService calculadoraFreteService;

    public CotacaoResponse gerar(Long clienteId, CotacaoRequest request) {

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado."));

        Cotacao cotacao = Cotacao.builder()
                .cepOrigem(request.cepOrigem())
                .cepDestino(request.cepDestino())
                .pesoKg(request.pesoKg())
                .valorFrete(calculadoraFreteService.calcularValor(request.pesoKg()))
                .prazoDias(calculadoraFreteService.calcularPrazo(request.cepDestino()))
                .status(StatusCotacao.GERADA)
                .criadaEm(LocalDateTime.now())
                .cliente(cliente)
                .build();

        Cotacao cotacaoSalva = cotacaoRepository.save(cotacao);

        return new CotacaoResponse(
                cotacaoSalva.getId(),
                cotacaoSalva.getCepOrigem(),
                cotacaoSalva.getCepDestino(),
                cotacaoSalva.getPesoKg(),
                cotacaoSalva.getValorFrete(),
                cotacaoSalva.getPrazoDias(),
                cotacaoSalva.getStatus(),
                cotacaoSalva.getCriadaEm()
        );
    }

    public List<CotacaoResponse> listarPorCliente(Long clienteId) {

        if (!clienteRepository.existsById(clienteId)) {
            throw new RecursoNaoEncontradoException("Cliente não encontrado.");
        }

        return cotacaoRepository.findByClienteId(clienteId)
                .stream()
                .map(cotacao -> new CotacaoResponse(
                        cotacao.getId(),
                        cotacao.getCepOrigem(),
                        cotacao.getCepDestino(),
                        cotacao.getPesoKg(),
                        cotacao.getValorFrete(),
                        cotacao.getPrazoDias(),
                        cotacao.getStatus(),
                        cotacao.getCriadaEm()
                ))
                .toList();
    }

    public CotacaoResponse buscarPorId(Long clienteId, Long cotacaoId) {

        if (!clienteRepository.existsById(clienteId)) {
            throw new RecursoNaoEncontradoException("Cliente não encontrado.");
        }

        Cotacao cotacao = cotacaoRepository.findById(cotacaoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cotação não encontrada."));

        if (!cotacao.getCliente().getId().equals(clienteId)) {
            throw new RecursoNaoEncontradoException("Cotação não encontrada para este cliente.");
        }

        return new CotacaoResponse(
                cotacao.getId(),
                cotacao.getCepOrigem(),
                cotacao.getCepDestino(),
                cotacao.getPesoKg(),
                cotacao.getValorFrete(),
                cotacao.getPrazoDias(),
                cotacao.getStatus(),
                cotacao.getCriadaEm()
        );
    }

    public CotacaoResponse cancelar(Long clienteId, Long cotacaoId) {

        if (!clienteRepository.existsById(clienteId)) {
            throw new RecursoNaoEncontradoException("Cliente não encontrado.");
        }

        Cotacao cotacao = cotacaoRepository.findById(cotacaoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cotação não encontrada."));

        if (!cotacao.getCliente().getId().equals(clienteId)) {
            throw new RecursoNaoEncontradoException("Cotação não encontrada para este cliente.");
        }

        if (cotacao.getStatus() == StatusCotacao.CANCELADA) {
            throw new RuntimeException("A cotação já está cancelada.");
        }

        cotacao.setStatus(StatusCotacao.CANCELADA);

        Cotacao cotacaoCancelada = cotacaoRepository.save(cotacao);

        return new CotacaoResponse(
                cotacaoCancelada.getId(),
                cotacaoCancelada.getCepOrigem(),
                cotacaoCancelada.getCepDestino(),
                cotacaoCancelada.getPesoKg(),
                cotacaoCancelada.getValorFrete(),
                cotacaoCancelada.getPrazoDias(),
                cotacaoCancelada.getStatus(),
                cotacaoCancelada.getCriadaEm()
        );
    }

}