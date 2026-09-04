package com.senai.suporte.suporte.service;

import java.util.List;

import com.senai.suporte.suporte.exception.RecursoNaoEncontradoException;
import com.senai.suporte.suporte.model.Solicitacao;
import com.senai.suporte.suporte.model.Solicitacao.StatusSolicitacao;
import com.senai.suporte.suporte.model.Solicitacao.TipoProblema;
import com.senai.suporte.suporte.repository.PainelTecnicoRepository;
import com.senai.suporte.suporte.repository.SolicitacaoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SolicitacaoService {

    private final SolicitacaoRepository solicitacaoRepository;
    private final PainelTecnicoRepository painelTecnicoRepository;

    public SolicitacaoService(
            SolicitacaoRepository solicitacaoRepository,
            PainelTecnicoRepository painelTecnicoRepository
    ) {
        this.solicitacaoRepository = solicitacaoRepository;
        this.painelTecnicoRepository = painelTecnicoRepository;
    }

    @Transactional
    public Solicitacao salvar(Solicitacao solicitacao) {

        solicitacao.setStatus(StatusSolicitacao.PENDENTE);

        return solicitacaoRepository.save(solicitacao);
    }

    @Transactional(readOnly = true)
    public List<Solicitacao> listarTodas() {
        return solicitacaoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Solicitacao buscarPorId(Long id) {
        return solicitacaoRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Solicitação",
                                id
                        )
                );
    }

    @Transactional(readOnly = true)
    public List<Solicitacao> filtrar(
            TipoProblema tipo,
            StatusSolicitacao status
    ) {
        if (tipo != null && status != null) {
            return solicitacaoRepository
                    .findByStatusAndTipoProblema(status, tipo);
        }

        if (tipo != null) {
            return solicitacaoRepository
                    .findByTipoProblema(tipo);
        }

        if (status != null) {
            return solicitacaoRepository.findByStatus(status);
        }

        return solicitacaoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Solicitacao> buscarPorNome(String nome) {
        if (nome == null || nome.isBlank()) {
            return solicitacaoRepository.findAll();
        }

        return solicitacaoRepository
                .buscarPorNome(nome.trim());
    }

    @Transactional
    public Solicitacao atualizar(
            Long id,
            Solicitacao dadosNovos
    ) {
        Solicitacao existente = buscarPorId(id);

        existente.setNif(dadosNovos.getNif());

        existente.setNomeSolicitante(
                dadosNovos.getNomeSolicitante()
        );

        existente.setNumeroSala(
                dadosNovos.getNumeroSala()
        );

        existente.setCodigoPatrimonio(
                dadosNovos.getCodigoPatrimonio()
        );

        existente.setDescricaoProblema(
                dadosNovos.getDescricaoProblema()
        );

        existente.setTipoProblema(
                dadosNovos.getTipoProblema()
        );

        existente.setStatus(
                dadosNovos.getStatus()
        );

        return solicitacaoRepository.save(existente);
    }

    @Transactional
    public Solicitacao alterarStatus(
            Long id,
            StatusSolicitacao novoStatus
    ) {
        Solicitacao solicitacao = buscarPorId(id);

        solicitacao.setStatus(novoStatus);

        return solicitacaoRepository.save(solicitacao);
    }

    @Transactional
    public void excluir(Long id) {

        Solicitacao solicitacao = buscarPorId(id);

        painelTecnicoRepository
                .findBySolicitacaoId(id)
                .ifPresent(painelTecnicoRepository::delete);

        solicitacaoRepository.delete(solicitacao);
    }
}