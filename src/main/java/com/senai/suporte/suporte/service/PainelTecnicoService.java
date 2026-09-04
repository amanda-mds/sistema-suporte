package com.senai.suporte.suporte.service;

import java.util.Optional;

import com.senai.suporte.suporte.exception.RecursoNaoEncontradoException;
import com.senai.suporte.suporte.model.PainelTecnico;
import com.senai.suporte.suporte.model.Solicitacao;
import com.senai.suporte.suporte.model.Solicitacao.StatusSolicitacao;
import com.senai.suporte.suporte.repository.PainelTecnicoRepository;
import com.senai.suporte.suporte.repository.SolicitacaoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PainelTecnicoService {

    private final PainelTecnicoRepository painelTecnicoRepository;
    private final SolicitacaoRepository solicitacaoRepository;

    public PainelTecnicoService(
            PainelTecnicoRepository painelTecnicoRepository,
            SolicitacaoRepository solicitacaoRepository
    ) {
        this.painelTecnicoRepository = painelTecnicoRepository;
        this.solicitacaoRepository = solicitacaoRepository;
    }

    @Transactional
    public PainelTecnico assumir(
            Long solicitacaoId,
            String tecnicoResponsavel,
            String observacoes
    ) {
        Solicitacao solicitacao =
                buscarSolicitacao(solicitacaoId);

        if (solicitacao.getStatus()
                != StatusSolicitacao.PENDENTE) {

            throw new IllegalStateException(
                    "Esta solicitação não pode ser assumida. "
                            + "Status atual: "
                            + solicitacao.getStatus().getDescricao()
            );
        }

        PainelTecnico painel = new PainelTecnico();

        painel.setSolicitacao(solicitacao);
        painel.setTecnicoResponsavel(tecnicoResponsavel);
        painel.setObservacoes(observacoes);

        solicitacao.setStatus(
                StatusSolicitacao.EM_ANDAMENTO
        );

        solicitacaoRepository.save(solicitacao);

        return painelTecnicoRepository.save(painel);
    }

    @Transactional
    public void concluir(Long solicitacaoId) {
        Solicitacao solicitacao =
                buscarSolicitacao(solicitacaoId);

        if (solicitacao.getStatus()
                != StatusSolicitacao.EM_ANDAMENTO) {

            throw new IllegalStateException(
                    "Esta solicitação não pode ser concluída. "
                            + "Status atual: "
                            + solicitacao.getStatus().getDescricao()
            );
        }

        solicitacao.setStatus(
                StatusSolicitacao.CONCLUIDO
        );

        solicitacaoRepository.save(solicitacao);
    }

    @Transactional(readOnly = true)
    public Optional<PainelTecnico> buscarPorSolicitacao(
            Long solicitacaoId
    ) {
        return painelTecnicoRepository
                .findBySolicitacaoId(solicitacaoId);
    }

    private Solicitacao buscarSolicitacao(Long id) {
        return solicitacaoRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Solicitação",
                                id
                        )
                );
    }
}