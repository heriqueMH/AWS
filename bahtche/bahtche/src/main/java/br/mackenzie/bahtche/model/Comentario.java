package br.mackenzie.bahtche.model;

import jakarta.persistence.*;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String texto;
    private Date dataCriacao = new Date();

    @ManyToOne
    @JsonIgnore
    private Problema problema;

    @ManyToOne
    private Usuario administrador;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexto() {
        return this.texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getDataCriacao() {
        return this.dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Problema getProblema() {
        return this.problema;
    }

    public void setProblema(Problema problema) {
        this.problema = problema;
    }

    public Usuario getAdministrador() {
        return this.administrador;
    }

    public void setAdministrador(Usuario administrador) {
        this.administrador = administrador;
    }


}