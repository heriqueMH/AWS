package br.mackenzie.bahtche.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    // Tela inicial - Redireciona para login
    @GetMapping("/")
    public String homeRedirect() {
        return "/login.html";
    }

    // Login
    @GetMapping("/login")
    public String loginPage() {
        return "login.html";
    }

    // Cadastro de usuário
    @GetMapping("/cadastro")
    public String cadastroPage() {
        return "cadastro.html";
    }

    // Criação de reporte
    @GetMapping("/criacao-reporte")
    public String criacaoReportePage() {
        return "criacao-reporte.html";
    }

    // Lista do administrador
    @GetMapping("/lista-admin")
    public String listaAdminPage() {
        return "lista-admin.html";
    }

    // Lista do usuário
    @GetMapping("/lista-usuario")
    public String listaUsuarioPage() {
        return "lista-usuario.html";
    }

    // Relatório
    @GetMapping("/relatorio")
    public String relatorioPage() {
        return "relatorio.html";
    }
}
