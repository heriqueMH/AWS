package br.mackenzie.bahtche.dto;

import java.util.Date;

public class ComentarioDTO {
    private Long id;
    private String texto;
    private Date dataCriacao;
    private String nomeAdministrador;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }

    public Date getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(Date dataCriacao) { this.dataCriacao = dataCriacao; }

    public String getNomeAdministrador() { return nomeAdministrador; }
    public void setNomeAdministrador(String nomeAdministrador) { this.nomeAdministrador = nomeAdministrador; }
}
