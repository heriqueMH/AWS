package br.mackenzie.bahtche.dto;

import jakarta.validation.constraints.*;

public class ComentarioRequest {

    @NotBlank(message = "O comentário não pode estar vazio")
    @Size(min = 4, max = 500, message = "O comentário deve ter entre 4 e 500 caracteres")
    private String texto;

    @NotNull(message = "O ID do problema é obrigatório")
    private Long problemaId;

    @NotNull(message = "O ID do administrador é obrigatório")
    private Long administradorId;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Long getProblemaId() {
        return problemaId;
    }

    public void setProblemaId(Long problemaId) {
        this.problemaId = problemaId;
    }

    public Long getAdministradorId() {
        return administradorId;
    }

    public void setAdministradorId(Long administradorId) {
        this.administradorId = administradorId;
    }
}
