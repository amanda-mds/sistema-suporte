package com.senai.suporte.suporte.controller;

import com.senai.suporte.suporte.model.Tecnico;
import com.senai.suporte.suporte.service.TecnicoService;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final TecnicoService tecnicoService;

    public AuthController(TecnicoService tecnicoService) {
        this.tecnicoService = tecnicoService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/cadastro")
    public String exibirCadastro(Model model) {
        model.addAttribute("tecnico", new Tecnico());
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrar(
            @Valid @ModelAttribute("tecnico") Tecnico tecnico,
            BindingResult resultado,
            Model model,
            RedirectAttributes flash) {

        // Se os dados forem inválidos, retorna ao formulário.
        if (resultado.hasErrors()) {
            return "cadastro";
        }

        try {
            tecnicoService.cadastrar(tecnico);

            flash.addFlashAttribute(
                    "sucesso",
                    "Cadastro realizado com sucesso! Faça o login para continuar."
            );

            return "redirect:/login";

        } catch (Exception e) {
            // Exibe o erro no próprio formulário.
            model.addAttribute("erro", e.getMessage());
            return "cadastro";
        }
    }
}