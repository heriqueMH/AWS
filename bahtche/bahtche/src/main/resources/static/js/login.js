const API_URL = "http://52.71.87.155";

document.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('formLogin');


  form.addEventListener('submit', async function (event) {
    event.preventDefault();

    const email = document.getElementById('email').value;
    const senha = document.getElementById('senha').value;
    const acessarComoAdmin = document.getElementById('acessarComoAdmin').checked;

    try {
      const response = await fetch(`${API_URL}/usuarios/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, senha })
      });
      
      if (response.ok) {
        const usuario = await response.json();
        localStorage.setItem('usuarioLogado', JSON.stringify(usuario));

        if (acessarComoAdmin) {
          if (usuario.role === 'MODERADOR') {
            window.location.href = 'lista-admin.html';
          } else {
            alert('Você não possui permissão para acessar como administrador.');
            localStorage.removeItem('usuarioLogado');
          }
        } else {
          window.location.href = 'lista-usuario.html';
        }

      } else {
        alert('Usuário ou senha incorretos.');
      }
    } catch (error) {
      console.error('Erro ao fazer login:', error);
      alert('Erro ao fazer login.');
    }
  });
});
