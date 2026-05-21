package com.gft.hubops.adapters.in.web.handler;

import com.gft.hubops.adapters.in.web.auth.dto.MensagemResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.stream.Collectors;
import com.gft.hubops.domain.exception.RecursoNaoEncontradoException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<MensagemResponse> handleHandlerMethodValidationException(
            HandlerMethodValidationException ex
    ) {
        return ResponseEntity
                .badRequest()
                .body(new MensagemResponse("Parâmetro inválido na requisição."));
    }


    @ExceptionHandler(RuntimeException.class) // EMAIL JÁ EXISTE //
    public ResponseEntity<MensagemResponse> handleRuntimeException(RuntimeException ex) {

        MensagemResponse response =
                new MensagemResponse(ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // NOME SO PODE CONTER LETRAS //
    public ResponseEntity<MensagemResponse> handleValidationException(MethodArgumentNotValidException ex) {

        String mensagem = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(erro -> erro.getDefaultMessage())
                .collect(Collectors.joining("; "));

        return ResponseEntity
                .badRequest()
                .body(new MensagemResponse(mensagem));
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<MensagemResponse> handleRecursoNaoEncontradoException(
            RecursoNaoEncontradoException ex
    ) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new MensagemResponse(ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<MensagemResponse> handleIllegalArgumentException(
            IllegalArgumentException ex
    ) {

        return ResponseEntity
                .badRequest()
                .body(new MensagemResponse(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MensagemResponse> handleGenericException(Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new MensagemResponse("Erro interno do servidor. Entre em contato com o suporte."));
    }

}
