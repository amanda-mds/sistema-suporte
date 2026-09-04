package com.senai.suporte.suporte.controller;

import java.security.Principal;

import com.senai.suporte.suporte.model.Solicitacao;
import com.senai.suporte.suporte.model.Solicitacao.StatusSolicitacao;
import com.senai.suporte.suporte.model.Solicitacao.TipoProblema;
import com.senai.suporte.suporte.service.PainelTecnicoService;
import com.senai.suporte.suporte.service.SolicitacaoService;
import com.senai.suporte.suporte.service.TecnicoService;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/painel")
public class PainelTecnicoController {

    private final SolicitacaoService solicitacaoService;
    private final PainelTecnicoService painelTecnicoService;
    private final TecnicoService tecnicoService;

    public PainelTecnicoController(
            SolicitacaoService solicitacaoService,
            PainelTecnicoService painelTecnicoService,
            TecnicoService tecnicoService
    ) {
        this.solicitacaoService = solicitacaoService;
        this.painelTecnicoService = painelTecnicoService;
        this.tecnicoService = tecnicoService;
    }

    @GetMapping
    public String listar(
            @RequestParam(required = false) TipoProblema tipo,
            @RequestParam(required = false) StatusSolicitacao status,
            @RequestParam(required = false) String nome,
            Model model
    ) {
        if (nome != null && !nome.isBlank()) {
            model.addAttribute(
                    "solicitacoes",
                    solicitacaoService.buscarPorNome(nome)
            );
        } else {
            model.addAttribute(
                    "solicitacoes",
                    solicitacaoService.filtrar(tipo, status)
            );
        }

        model.addAttribute("tipos", TipoProblema.values());
        model.addAttribute("statusList", StatusSolicitacao.values());
        model.addAttribute("filtroTipo", tipo);
        model.addAttribute("filtroStatus", status);
        model.addAttribute("filtroNome", nome);

        return "painel/lista";
    }

    @GetMapping("/assumir/{id}")
    public String exibirFormularioAssumir(
            @PathVariable Long id,
            Model model,
            Principal principal
    ) {
        model.addAttribute(
                "solicitacao",
                solicitacaoService.buscarPorId(id)
        );

        model.addAttribute(
                "tecnicoLogado",
                tecnicoService.nomePorEmail(principal.getName())
        );

        return "painel/assumir";
    }

    @PostMapping("/assumir/{id}")
    public String assumir(
            @PathVariable Long id,
            @RequestParam String tecnicoResponsavel,
            @RequestParam(required = false) String observacoes,
            RedirectAttributes flash
    ) {
        try {
            painelTecnicoService.assumir(
                    id,
                    tecnicoResponsavel,
                    observacoes
            );

            flash.addFlashAttribute(
                    "sucesso",
                    "Chamado assumido com sucesso!"
            );

        } catch (Exception e) {
            flash.addFlashAttribute(
                    "erro",
                    e.getMessage()
            );
        }

        return "redirect:/painel";
    }

    @PostMapping("/concluir/{id}")
    public String concluir(
            @PathVariable Long id,
            RedirectAttributes flash
    ) {
        try {
            painelTecnicoService.concluir(id);

            flash.addFlashAttribute(
                    "sucesso",
                    "Solicitação concluída com sucesso!"
            );

        } catch (Exception e) {
            flash.addFlashAttribute(
                    "erro",
                    e.getMessage()
            );
        }

        return "redirect:/painel";
    }

    @GetMapping("/editar/{id}")
    public String exibirFormularioEditar(
            @PathVariable Long id,
            Model model
    ) {
        model.addAttribute(
                "solicitacao",
                solicitacaoService.buscarPorId(id)
        );

        model.addAttribute(
                "tipos",
                TipoProblema.values()
        );

        model.addAttribute(
                "statusList",
                StatusSolicitacao.values()
        );

        return "painel/editar";
    }

    @PostMapping("/editar/{id}")
    public String salvarEdicao(
            @PathVariable Long id,
            @Valid @ModelAttribute("solicitacao") Solicitacao solicitacao,
            BindingResult resultado,
            Model model,
            RedirectAttributes flash
    ) {
        if (resultado.hasErrors()) {
            solicitacao.setId(id);

            model.addAttribute(
                    "tipos",
                    TipoProblema.values()
            );

            model.addAttribute(
                    "statusList",
                    StatusSolicitacao.values()
            );

            return "painel/editar";
        }

        try {
            solicitacaoService.atualizar(
                    id,
                    solicitacao
            );

            flash.addFlashAttribute(
                    "sucesso",
                    "Solicitação atualizada com sucesso!"
            );

        } catch (Exception e) {
            flash.addFlashAttribute(
                    "erro",
                    e.getMessage()
            );
        }

        return "redirect:/painel";
    }

    @GetMapping("/excluir/{id}")
    public String confirmarExclusao(
            @PathVariable Long id,
            Model model
    ) {
        model.addAttribute(
                "solicitacao",
                solicitacaoService.buscarPorId(id)
        );

        return "painel/confirmar-exclusao";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(
            @PathVariable Long id,
            RedirectAttributes flash
    ) {
        try {
            solicitacaoService.excluir(id);

            flash.addFlashAttribute(
                    "sucesso",
                    "Solicitação excluída com sucesso!"
            );

        } catch (Exception e) {
            flash.addFlashAttribute(
                    "erro",
                    e.getMessage()
            );
        }

        return "redirect:/painel";
    }
}