package br.mackenzie.bahtche.dto;

import jakarta.validation.constraints.*;

public class UsuarioDTO {

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 4, max = 50, message = "O nome deve ter entre 4 e 50 caracteres")
    @Pattern(regexp = "[A-Za-zÀ-ÿ\\s]+", message = "O nome deve conter apenas letras e espaços")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Formato de e-mail inválido")
    @Size(max = 100, message = "O e-mail pode ter no máximo 100 caracteres")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 4, max = 30, message = "A senha deve ter entre 4 e 30 caracteres")
    private String senha;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
