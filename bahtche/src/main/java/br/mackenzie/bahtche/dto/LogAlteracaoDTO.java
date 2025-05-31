package br.mackenzie.bahtche.dto;

import java.util.Date;

import br.mackenzie.bahtche.model.Endereco;
import br.mackenzie.bahtche.model.LogAlteracao;

public class LogAlteracaoDTO {
    private Long id;
    private String descricao;
    private Date dataAlteracao;
    private String nomeAdministrador;
    private String tipoAcao;

    private String tituloProblema;
    private String categoriaProblema;
    private String enderecoCompleto;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private Date dataCriacaoProblema;
    private String nomeUsuarioCriador;

    public LogAlteracaoDTO() {}

    public LogAlteracaoDTO(LogAlteracao log) {
        this.id = log.getId();
        this.descricao = log.getDescricao();
        this.dataAlteracao = log.getDataAlteracao();

        if (log.getAdministrador() != null) {
            this.nomeAdministrador = log.getAdministrador().getNome();
        }

        if (log.getProblema() != null) {
            this.tipoAcao = "PROBLEMA";
            this.tituloProblema = log.getProblema().getTitulo();
            this.categoriaProblema = log.getProblema().getCategoria();
            this.dataCriacaoProblema = log.getProblema().getDataCriacao();

            if (log.getProblema().getEndereco() != null) {
                Endereco e = log.getProblema().getEndereco();
                this.rua = e.getRua();
                this.numero = e.getNumero();
                this.bairro = e.getBairro();
                this.cidade = e.getCidade();
                this.estado = e.getEstado();
                this.cep = e.getCep();

                this.enderecoCompleto = String.format(
                    "%s, %s, %s, %s – %s (%s)",
                    this.rua, this.numero, this.bairro, this.cidade, this.estado, this.cep
                );
            }

            if (log.getProblema().getUsuario() != null) {
                this.nomeUsuarioCriador = log.getProblema().getUsuario().getNome();
            }
        } else {
            this.tipoAcao = "USUARIO";
        }
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataAlteracao() {
        return this.dataAlteracao;
    }

    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public String getNomeAdministrador() {
        return this.nomeAdministrador;
    }

    public void setNomeAdministrador(String nomeAdministrador) {
        this.nomeAdministrador = nomeAdministrador;
    }

    public String getTipoAcao() {
        return this.tipoAcao;
    }

    public void setTipoAcao(String tipoAcao) {
        this.tipoAcao = tipoAcao;
    }

    public String getTituloProblema() {
        return this.tituloProblema;
    }

    public void setTituloProblema(String tituloProblema) {
        this.tituloProblema = tituloProblema;
    }

    public String getCategoriaProblema() {
        return this.categoriaProblema;
    }

    public void setCategoriaProblema(String categoriaProblema) {
        this.categoriaProblema = categoriaProblema;
    }

    public String getEnderecoCompleto() {
        return this.enderecoCompleto;
    }

    public void setEnderecoCompleto(String enderecoCompleto) {
        this.enderecoCompleto = enderecoCompleto;
    }

    public String getRua() {
        return this.rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return this.numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return this.bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return this.cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return this.cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Date getDataCriacaoProblema() {
        return this.dataCriacaoProblema;
    }

    public void setDataCriacaoProblema(Date dataCriacaoProblema) {
        this.dataCriacaoProblema = dataCriacaoProblema;
    }

    public String getNomeUsuarioCriador() {
        return this.nomeUsuarioCriador;
    }

    public void setNomeUsuarioCriador(String nomeUsuarioCriador) {
        this.nomeUsuarioCriador = nomeUsuarioCriador;
    }

}
