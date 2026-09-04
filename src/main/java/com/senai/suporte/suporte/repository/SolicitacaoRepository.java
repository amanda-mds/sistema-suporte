package com.senai.suporte.suporte.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.senai.suporte.suporte.model.Solicitacao;
import com.senai.suporte.suporte.model.Solicitacao.StatusSolicitacao;
import com.senai.suporte.suporte.model.Solicitacao.TipoProblema;

public interface SolicitacaoRepository
        extends JpaRepository<Solicitacao, Long> {

    List<Solicitacao> findByStatus(StatusSolicitacao status);

    List<Solicitacao> findByTipoProblema(TipoProblema tipoProblema);

    List<Solicitacao> findByStatusAndTipoProblema(
            StatusSolicitacao status,
            TipoProblema tipoProblema
    );

    @Query("""
            SELECT s
            FROM Solicitacao s
            WHERE LOWER(s.nomeSolicitante)
            LIKE LOWER(CONCAT('%', :nome, '%'))
            """)
    List<Solicitacao> buscarPorNome(@Param("nome") String nome);
}