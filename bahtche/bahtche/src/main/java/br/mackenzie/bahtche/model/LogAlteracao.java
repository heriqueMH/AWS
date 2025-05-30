package br.mackenzie.bahtche.model;

import jakarta.persistence.*;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class LogAlteracao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private Date dataAlteracao = new Date();

    @ManyToOne
    private Usuario administrador;

    @ManyToOne
    @JsonIgnore
    private Problema problema;

    public LogAlteracao() {}

    public LogAlteracao(String descricao, Usuario administrador, Problema problema) {
        this.descricao = descricao;
        this.administrador = administrador;
        this.problema = problema;
    }

    public Long getId() { return id; }
    public String getDescricao() { return descricao; }
    public Date getDataAlteracao() { return dataAlteracao; }
    public Usuario getAdministrador() { return administrador; }
    public Problema getProblema() { return problema; }

    public void setId(Long id) { this.id = id; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setDataAlteracao(Date dataAlteracao) { this.dataAlteracao = dataAlteracao; }
    public void setAdministrador(Usuario administrador) { this.administrador = administrador; }
    public void setProblema(Problema problema) { this.problema = problema; }
}