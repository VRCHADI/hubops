package com.gft.hubops.adapters.in.web.handler;

import com.gft.hubops.adapters.in.web.auth.dto.MensagemResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

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

}