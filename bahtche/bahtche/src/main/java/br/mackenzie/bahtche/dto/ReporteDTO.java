package br.mackenzie.bahtche.dto;

import jakarta.validation.constraints.*;

public class ReporteDTO {

    @NotBlank(message = "O título é obrigatório")
    @Size(min = 4, max = 100, message = "O título deve ter entre 4 e 100 caracteres")
    private String titulo;

    @NotBlank(message = "A descrição é obrigatória")
    @Size(min = 4, max = 1000, message = "A descrição deve ter entre 4 e 1000 caracteres")
    private String descricao;

    @NotNull(message = "ID do usuário é obrigatório")
    private Long usuarioId;

    // Getters e Setters
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

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}
