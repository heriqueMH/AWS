package br.mackenzie.bahtche.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Problema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private String categoria;
    private String status = "Aguardando Análise";
    private Date dataCriacao;
    private Date dataAtualizacao;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Embedded
    private Endereco endereco;

    @OneToMany(mappedBy = "problema", cascade = CascadeType.ALL)
    private List<Comentario> comentarios;

    @OneToMany(mappedBy = "problema", cascade = CascadeType.ALL)
    private List<LogAlteracao> logAlteracoes;

    @PrePersist
    protected void onCreate() {
        this.dataCriacao = new Date();
    }
    
    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}