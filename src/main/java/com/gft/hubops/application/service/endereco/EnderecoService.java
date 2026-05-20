package com.gft.hubops.application.service.endereco;

import com.gft.hubops.adapters.in.web.endereco.dto.EnderecoRequest;
import com.gft.hubops.adapters.in.web.endereco.dto.EnderecoResponse;
import com.gft.hubops.adapters.out.persistence.cliente.ClienteRepository;
import com.gft.hubops.adapters.out.persistence.endereco.EnderecoRepository;
import com.gft.hubops.domain.model.cliente.Cliente;
import com.gft.hubops.domain.model.endereco.Endereco;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import com.gft.hubops.domain.exception.RecursoNaoEncontradoException;
import com.gft.hubops.adapters.out.integration.viacep.ViaCepClient;
import com.gft.hubops.adapters.out.integration.viacep.dto.ViaCepResponse;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final ClienteRepository clienteRepository;
    private final ViaCepClient viaCepClient;

    public EnderecoResponse cadastrar(Long clienteId, EnderecoRequest request) {

        ViaCepResponse viaCep = viaCepClient.buscarCep(request.cep());

        if (Boolean.TRUE.equals(viaCep.erro())) {
            throw new RuntimeException("CEP não encontrado.");
        }

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado."));

        Endereco endereco = Endereco.builder()
                .cep(request.cep())
                .rua(viaCep.rua())
                .numero(request.numero())
                .complemento(request.complemento())
                .bairro(viaCep.bairro())
                .cidade(viaCep.cidade())
                .estado(viaCep.estado())
                .cliente(cliente)
                .build();

        Endereco enderecoSalvo = enderecoRepository.save(endereco);

        return new EnderecoResponse(
                enderecoSalvo.getId(),
                enderecoSalvo.getCep(),
                enderecoSalvo.getRua(),
                enderecoSalvo.getNumero(),
                enderecoSalvo.getComplemento(),
                enderecoSalvo.getBairro(),
                enderecoSalvo.getCidade(),
                enderecoSalvo.getEstado(),
                enderecoSalvo.getCliente().getId()
        );
    }

    public List<EnderecoResponse> listarPorCliente(Long clienteId) {

        if (!clienteRepository.existsById(clienteId)) {
            throw new RuntimeException("Cliente não encontrado.");
        }

        return enderecoRepository.findByClienteId(clienteId)
                .stream()
                .map(endereco -> new EnderecoResponse(
                        endereco.getId(),
                        endereco.getCep(),
                        endereco.getRua(),
                        endereco.getNumero(),
                        endereco.getComplemento(),
                        endereco.getBairro(),
                        endereco.getCidade(),
                        endereco.getEstado(),
                        endereco.getCliente().getId()
                ))
                .toList();
    }

    public EnderecoResponse buscarPorId(Long clienteId, Long enderecoId) {

        if (!clienteRepository.existsById(clienteId)) {
            throw new RecursoNaoEncontradoException("Cliente não encontrado.");
        }

        Endereco endereco = enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Endereço não encontrado."));

        if (!endereco.getCliente().getId().equals(clienteId)) {
            throw new RecursoNaoEncontradoException("Endereço não encontrado para este cliente.");
        }

        return new EnderecoResponse(
                endereco.getId(),
                endereco.getCep(),
                endereco.getRua(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCliente().getId()
        );
    }

    public EnderecoResponse atualizar(
            Long clienteId,
            Long enderecoId,
            EnderecoRequest request
    ) {

        if (!clienteRepository.existsById(clienteId)) {
            throw new RecursoNaoEncontradoException("Cliente não encontrado.");
        }

        Endereco endereco = enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Endereço não encontrado."));

        if (!endereco.getCliente().getId().equals(clienteId)) {
            throw new RecursoNaoEncontradoException("Endereço não encontrado para este cliente.");
        }

        ViaCepResponse viaCep = viaCepClient.buscarCep(request.cep());

        if (Boolean.TRUE.equals(viaCep.erro())) {
            throw new RuntimeException("CEP não encontrado.");
        }

        endereco.setCep(viaCep.cep());
        endereco.setRua(viaCep.rua());
        endereco.setNumero(request.numero());
        endereco.setComplemento(request.complemento());
        endereco.setBairro(viaCep.bairro());
        endereco.setCidade(viaCep.cidade());
        endereco.setEstado(viaCep.estado());

        Endereco enderecoAtualizado = enderecoRepository.save(endereco);

        return new EnderecoResponse(
                enderecoAtualizado.getId(),
                enderecoAtualizado.getCep(),
                enderecoAtualizado.getRua(),
                enderecoAtualizado.getNumero(),
                enderecoAtualizado.getComplemento(),
                enderecoAtualizado.getBairro(),
                enderecoAtualizado.getCidade(),
                enderecoAtualizado.getEstado(),
                enderecoAtualizado.getCliente().getId()
        );
    }

    public void deletar(Long clienteId, Long enderecoId) {

        if (!clienteRepository.existsById(clienteId)) {
            throw new RecursoNaoEncontradoException("Cliente não encontrado.");
        }

        Endereco endereco = enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Endereço não encontrado."));

        if (!endereco.getCliente().getId().equals(clienteId)) {
            throw new RecursoNaoEncontradoException("Endereço não encontrado para este cliente.");
        }

        enderecoRepository.delete(endereco);
    }

}