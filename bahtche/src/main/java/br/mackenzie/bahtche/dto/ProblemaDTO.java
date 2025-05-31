package br.mackenzie.bahtche.dto;

import java.util.Date;
import br.mackenzie.bahtche.model.Endereco;
import br.mackenzie.bahtche.model.Problema;

public class ProblemaDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private String categoria;
    private String status;
    private Endereco endereco;
    private Long usuarioId;
    private Date dataCriacao;

    private String nomeUsuarioCriador;
    private Date dataUltimaAlteracao;
    private String nomeAdministradorUltimaAlteracao;
    private Date dataAtualizacao;

    public ProblemaDTO() {}

    public ProblemaDTO(Problema problema) {
        this.id = problema.getId();
        this.titulo = problema.getTitulo();
        this.descricao = problema.getDescricao();
        this.categoria = problema.getCategoria();
        this.status = problema.getStatus();
        this.endereco = problema.getEndereco();
        this.usuarioId = problema.getUsuario() != null ? problema.getUsuario().getId() : null;
        this.dataCriacao = problema.getDataCriacao();
        this.dataAtualizacao = problema.getDataAtualizacao();
    }

    public ProblemaDTO(Long id, String titulo, String descricao, String categoria, String status, Endereco endereco, Long usuarioId,
                       String nomeUsuarioCriador, Date dataUltimaAlteracao, String nomeAdministradorUltimaAlteracao,
                       Date dataCriacao, Date dataAtualizacao) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.categoria = categoria;
        this.status = status;
        this.endereco = endereco;
        this.usuarioId = usuarioId;
        this.nomeUsuarioCriador = nomeUsuarioCriador;
        this.dataUltimaAlteracao = dataUltimaAlteracao;
        this.nomeAdministradorUltimaAlteracao = nomeAdministradorUltimaAlteracao;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Endereco getEndereco() {
        return this.endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Long getUsuarioId() {
        return this.usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNomeUsuarioCriador() {
        return this.nomeUsuarioCriador;
    }

    public void setNomeUsuarioCriador(String nomeUsuarioCriador) {
        this.nomeUsuarioCriador = nomeUsuarioCriador;
    }

    public Date getDataUltimaAlteracao() {
        return this.dataUltimaAlteracao;
    }

    public void setDataUltimaAlteracao(Date dataUltimaAlteracao) {
        this.dataUltimaAlteracao = dataUltimaAlteracao;
    }

    public String getNomeAdministradorUltimaAlteracao() {
        return this.nomeAdministradorUltimaAlteracao;
    }

    public void setNomeAdministradorUltimaAlteracao(String nomeAdministradorUltimaAlteracao) {
        this.nomeAdministradorUltimaAlteracao = nomeAdministradorUltimaAlteracao;
    }

    public Date getDataAtualizacao() {
        return this.dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Date getDataCriacao() {
        return this.dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}