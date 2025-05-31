const API_URL = "http://52.71.87.155";

document.addEventListener('DOMContentLoaded', () => {
    verificarAutenticacao();
    carregarUsuarios();
  });
  
  async function carregarUsuarios() {
    try {
      const response = await fetch(`${API_URL}/usuarios`);
      const usuarios = await response.json();
  
      const container = document.getElementById('lista-usuarios');
      container.innerHTML = '';
  
      if (usuarios.length === 0) {
        container.innerHTML = '<p>Nenhum usuário cadastrado.</p>';
        return;
      }
  
      const tabela = document.createElement('table');
      tabela.className = 'tabela-usuarios';
  
      const thead = `
        <thead>
          <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Email</th>
            <th>Status</th>
            <th>Tipo</th>
            <th>Ações</th>
          </tr>
        </thead>
      `;
  
      const tbody = document.createElement('tbody');
      usuarios.forEach(usuario => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
          <td>${usuario.id}</td>
          <td>${usuario.nome}</td>
          <td>${usuario.email}</td>
          <td>${usuario.ativo ? 'Ativo' : 'Desativado'}</td>
          <td>${usuario.role}</td>
          <td id="acoes" class="acoes">
            <button class="desativar" onclick="alterarStatus(${usuario.id}, ${usuario.ativo})">
              ${usuario.ativo ? 'Desativar' : 'Ativar'}
            </button>
            ${usuario.role === 'USUARIO' ? `<button class="promover" onclick="promover(${usuario.id})">Promover</button>` : ''}
            ${usuario.role === 'MODERADOR' ? `<button class="rebaixar" onclick="rebaixar(${usuario.id})">Rebaixar</button>` : ''}
          </td>
        `;
        tbody.appendChild(tr);
      });
  
      tabela.innerHTML = thead;
      tabela.appendChild(tbody);
      container.appendChild(tabela);
  
    } catch (error) {
      console.error('Erro ao carregar usuários:', error);
      alert('Erro ao carregar lista de usuários.');
    }
  }
  
  async function alterarStatus(id, ativo) {
    const moderador = JSON.parse(localStorage.getItem("usuarioLogado"));
  
    try {
      await fetch(`${API_URL}/usuarios/${id}/status?moderadorId=${moderador.id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ ativo: !ativo })
      });
      carregarUsuarios();
    } catch (error) {
      console.error('Erro ao atualizar status:', error);
    }
  }
  
  async function promover(id) {
    const moderador = JSON.parse(localStorage.getItem("usuarioLogado"));
  
    try {
      await fetch(`${API_URL}/usuarios/${id}/promover?moderadorId=${moderador.id}`, {
        method: 'PUT'
      });
      carregarUsuarios();
    } catch (error) {
      console.error('Erro ao promover usuário:', error);
    }
  }
  
  async function rebaixar(id) {
    const moderador = JSON.parse(localStorage.getItem("usuarioLogado"));
  
    try {
      await fetch(`${API_URL}/usuarios/${id}/rebaixar?moderadorId=${moderador.id}`, {
        method: 'PUT'
      });
      carregarUsuarios();
    } catch (error) {
      console.error('Erro ao rebaixar usuário:', error);
    }
  }
  
  
  function logout() {
    localStorage.removeItem('usuarioLogado');
    window.location.href = 'login.html';
  }
  
  function verificarAutenticacao() {
    const usuarioLogado = JSON.parse(localStorage.getItem('usuarioLogado'));
    if (!usuarioLogado) {
      alert('Você precisa estar logado para acessar esta página.');
      window.location.href = 'login.html';
    }
    if (usuarioLogado.role !== 'MODERADOR') {
      alert('Acesso restrito para moderadores.');
      window.location.href = 'lista-usuario.html';
    }
  }
  
  document.getElementById('lista-admin').addEventListener('click', () => {
    window.location.href = 'lista-admin.html';
  });
  
  