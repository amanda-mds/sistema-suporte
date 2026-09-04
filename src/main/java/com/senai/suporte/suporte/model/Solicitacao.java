package com.senai.suporte.suporte.model;

import java.util.Objects;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "solicitacao")
public class Solicitacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "NIF é obrigatório")
    @Column(nullable = false)
    private String nif;

    @NotBlank(message = "Nome do solicitante é obrigatório")
    @Size(min = 3, max = 100,
            message = "Nome deve ter entre 3 e 100 caracteres")
    @Column(nullable = false)
    private String nomeSolicitante;

    @NotBlank(message = "O número da sala é obrigatório")
    @Column(nullable = false)
    private String numeroSala;

    @NotBlank(message = "O código do patrimônio é obrigatório")
    @Column(nullable = false)
    private String codigoPatrimonio;

    @NotBlank(message = "A descrição é obrigatória")
    @Column(nullable = false)
    private String descricaoProblema;

    @NotNull(message = "O tipo de problema é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoProblema tipoProblema;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusSolicitacao status = StatusSolicitacao.PENDENTE;

    public Solicitacao() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getNomeSolicitante() {
        return nomeSolicitante;
    }

    public void setNomeSolicitante(String nomeSolicitante) {
        this.nomeSolicitante = nomeSolicitante;
    }

    public String getNumeroSala() {
        return numeroSala;
    }

    public void setNumeroSala(String numeroSala) {
        this.numeroSala = numeroSala;
    }

    public String getCodigoPatrimonio() {
        return codigoPatrimonio;
    }

    public void setCodigoPatrimonio(String codigoPatrimonio) {
        this.codigoPatrimonio = codigoPatrimonio;
    }

    public String getDescricaoProblema() {
        return descricaoProblema;
    }

    public void setDescricaoProblema(String descricaoProblema) {
        this.descricaoProblema = descricaoProblema;
    }

    public TipoProblema getTipoProblema() {
        return tipoProblema;
    }

    public void setTipoProblema(TipoProblema tipoProblema) {
        this.tipoProblema = tipoProblema;
    }

    public StatusSolicitacao getStatus() {
        return status;
    }

    public void setStatus(StatusSolicitacao status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Solicitacao that = (Solicitacao) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass());
    }

    public enum TipoProblema {

        INFORMATICA("Informática"),
        ELETRICA("Elétrica"),
        ZELADORIA("Zeladoria");

        private final String descricao;

        TipoProblema(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    public enum StatusSolicitacao {

        PENDENTE("Pendente"),
        EM_ANDAMENTO("Em andamento"),
        CONCLUIDO("Concluído");

        private final String descricao;

        StatusSolicitacao(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }
}