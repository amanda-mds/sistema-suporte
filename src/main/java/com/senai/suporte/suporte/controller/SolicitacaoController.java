package com.senai.suporte.suporte.controller;

import com.senai.suporte.suporte.model.Solicitacao;
import com.senai.suporte.suporte.service.SolicitacaoService;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/solicitacao")
public class SolicitacaoController {

    private final SolicitacaoService solicitacaoService;

    public SolicitacaoController(
            SolicitacaoService solicitacaoService
    ) {
        this.solicitacaoService = solicitacaoService;
    }

    @GetMapping
    public String exibirFormulario(Model model) {
        model.addAttribute("solicitacao", new Solicitacao());

        model.addAttribute(
                "tipos",
                Solicitacao.TipoProblema.values()
        );

        return "solicitacao/formulario";
    }

    @PostMapping
    public String salvar(
            @Valid @ModelAttribute("solicitacao") Solicitacao solicitacao,
            BindingResult resultado,
            Model model,
            RedirectAttributes flash) {

        if (resultado.hasErrors()) {
            // A lista precisa ser adicionada novamente quando há erro.
            model.addAttribute(
                    "tipos",
                    Solicitacao.TipoProblema.values()
            );

            return "solicitacao/formulario";
        }

        try {
            solicitacaoService.salvar(solicitacao);

            flash.addFlashAttribute(
                    "sucesso",
                    "Chamado aberto com sucesso! Em breve, um técnico irá atendê-lo."
            );

            return "redirect:/solicitacao";

        } catch (Exception e) {
            model.addAttribute("erro", e.getMessage());

            model.addAttribute(
                    "tipos",
                    Solicitacao.TipoProblema.values()
            );

            return "solicitacao/formulario";
        }
    }
}