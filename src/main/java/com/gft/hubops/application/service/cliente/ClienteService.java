package com.gft.hubops.application.service.cliente;

import com.gft.hubops.adapters.in.web.cliente.dto.ClienteRequest;
import com.gft.hubops.adapters.in.web.cliente.dto.ClienteResponse;
import com.gft.hubops.adapters.out.persistence.cliente.ClienteRepository;
import com.gft.hubops.domain.model.cliente.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDateTime;
import com.gft.hubops.domain.exception.RecursoNaoEncontradoException;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteResponse cadastrar(ClienteRequest request) {
        if (clienteRepository.findByEmail(request.email()).isPresent()) {
            throw new RuntimeException("E-mail já cadastrado.");
        }

        if (clienteRepository.findByDocumento(request.documento()).isPresent()) {
            throw new RuntimeException("Documento já cadastrado.");
        }

        Cliente cliente = Cliente.builder()
                .nome(request.nome())
                .email(request.email())
                .documento(request.documento())
                .criadoEm(LocalDateTime.now())
                .build();


        Cliente clienteSalvo = clienteRepository.save(cliente);

        return new ClienteResponse(
                clienteSalvo.getId(),
                clienteSalvo.getNome(),
                clienteSalvo.getEmail(),
                clienteSalvo.getDocumento(),
                clienteSalvo.getCriadoEm()
        );
    }

    public List<ClienteResponse> listarTodos() {
        return clienteRepository.findAll()
                .stream()
                .map(cliente -> new ClienteResponse(
                        cliente.getId(),
                        cliente.getNome(),
                        cliente.getEmail(),
                        cliente.getDocumento(),
                        cliente.getCriadoEm()
                ))
                .toList();
    }

    public ClienteResponse buscarPorId(Long id) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado."));

        return new ClienteResponse(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getDocumento(),
                cliente.getCriadoEm()
        );
    }

    public ClienteResponse atualizar(Long id, ClienteRequest request) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado."));

        clienteRepository.findByEmail(request.email())
                .filter(clienteEncontrado -> !clienteEncontrado.getId().equals(id))
                .ifPresent(clienteEncontrado -> {
                    throw new RuntimeException("E-mail já cadastrado para outro cliente.");
                });

        clienteRepository.findByDocumento(request.documento())
                .filter(clienteEncontrado -> !clienteEncontrado.getId().equals(id))
                .ifPresent(clienteEncontrado -> {
                    throw new RuntimeException("Documento já cadastrado para outro cliente.");
                });

        cliente.setNome(request.nome());
        cliente.setEmail(request.email());
        cliente.setDocumento(request.documento());

        Cliente clienteAtualizado = clienteRepository.save(cliente);

        return new ClienteResponse(
                clienteAtualizado.getId(),
                clienteAtualizado.getNome(),
                clienteAtualizado.getEmail(),
                clienteAtualizado.getDocumento(),
                clienteAtualizado.getCriadoEm()
        );
    }

}