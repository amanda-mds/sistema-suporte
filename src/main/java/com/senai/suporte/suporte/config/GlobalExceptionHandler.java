package com.senai.suporte.suporte.config;

import com.senai.suporte.suporte.exception.RecursoNaoEncontradoException;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String tratarRecursoNaoEncontrado(
            RecursoNaoEncontradoException exception,
            Model model
    ) {
        model.addAttribute(
                "mensagemErro",
                exception.getMessage()
        );

        return "erro";
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String tratarErroInesperado(
            RuntimeException exception,
            Model model
    ) {
        String mensagem = exception.getMessage();

        if (mensagem == null || mensagem.isBlank()) {
            mensagem = "Ocorreu um erro inesperado no sistema.";
        }

        model.addAttribute("mensagemErro", mensagem);

        return "erro";
    }
}