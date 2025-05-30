const API_URL = "http://52.71.87.155";

document.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('formCadastro');


  form.addEventListener('submit', async function (event) {
    event.preventDefault();

    const nome = document.getElementById('nome').value.trim();
    const email = document.getElementById('email').value.trim();
    const senha = document.getElementById('senha').value;
    const confirmarSenha = document.getElementById('confirmarSenha').value;

    if (senha !== confirmarSenha) {
      alert('As senhas não coincidem.');
      return;
    }

    const usuario = {
      nome,
      email,
      senha,
      ativo: true,
      role: 'USUARIO'
    };

    try {
      const response = await fetch(`${API_URL}/usuarios`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(usuario)
      });

      if (!response.ok) {
        const msg = await response.text();
        throw new Error(msg || 'Erro ao cadastrar usuário');
      }

      alert('Usuário cadastrado com sucesso!');
      window.location.href = 'login.html';

    } catch (error) {
      alert(`Erro: ${error.message}`);
    }
  });
});

