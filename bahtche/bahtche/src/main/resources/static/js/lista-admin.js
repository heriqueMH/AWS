const API_URL = "http://52.71.87.155";

document.addEventListener('DOMContentLoaded', async () => {
  const container = document.getElementById('tabelaProblemas');
  const usuario = JSON.parse(localStorage.getItem('usuarioLogado'));

  const response = await fetch(`${API_URL}/problemas`);
  const problemas = await response.json();

  preencherFiltros(problemas);
  renderizarLista(problemas);

  document.getElementById('filtroBairro').addEventListener('change', () => aplicarFiltros(problemas));
  document.getElementById('filtroCategoria').addEventListener('change', () => aplicarFiltros(problemas));
  document.getElementById('filtroStatus').addEventListener('change', () => aplicarFiltros(problemas));
});

function preencherFiltros(problemas) {
  const bairros = new Set();
  const categorias = new Set();
  const status = new Set();

  problemas.forEach(p => {
    if (p.endereco?.bairro) bairros.add(p.endereco.bairro);
    if (p.categoria) categorias.add(p.categoria);
    if (p.status) status.add(p.status);
  });

  bairros.forEach(b => addOption('filtroBairro', b));
  categorias.forEach(c => addOption('filtroCategoria', c));
  status.forEach(s => addOption('filtroStatus', s));
}

function addOption(id, value) {
  const select = document.getElementById(id);
  const option = document.createElement('option');
  option.value = value;
  option.textContent = value;
  select.appendChild(option);
}

function aplicarFiltros(problemas) {
  const bairro = document.getElementById('filtroBairro').value;
  const categoria = document.getElementById('filtroCategoria').value;
  const status = document.getElementById('filtroStatus').value;

  const filtrados = problemas.filter(p => {
    return (!bairro || p.endereco?.bairro === bairro) &&
           (!categoria || p.categoria === categoria) &&
           (!status || p.status === status);
  });

  renderizarLista(filtrados);
}

function renderizarLista(problemas) {
  const container = document.getElementById('tabelaProblemas');
  container.innerHTML = '';

  problemas.forEach(p => {
    const linha = document.createElement('div');
    linha.className = 'card-reporte';
    linha.innerHTML = `
    <div class="reporte-info">
      <strong>${p.titulo}</strong>
      <p><strong>Endereço completo:</strong> ${p.endereco ? `${p.endereco.rua}, ${p.endereco.numero} - ${p.endereco.bairro}, ${p.endereco.cidade} - ${p.endereco.estado}, ${p.endereco.cep}` : '-'}</p>
      <p><strong>Tipo:</strong> ${p.categoria}</p>
      <p><strong>Status:</strong> ${p.status}</p>
      <select class="status-dropdown">
        <option value="Pendente">Pendente</option>
        <option value="Análise">Análise</option>
        <option value="Resolvido">Resolvido</option>
      </select>
      <button class="botao-detalhes">Detalhes</button>
    </div>
  
    <div class="detalhes-expandido">
      <p><strong>Descrição:</strong> ${p.descricao}</p>
      <p><strong>Criado por:</strong> ${p.nomeUsuarioCriador || 'Desconhecido'}</p>
      <p><strong>Informação de criação:</strong> ${formatarData(p.dataCriacao)}</p>
      <p><strong>Última alteração:</strong> ${formatarData(p.dataUltimaAlteracao)}</p>
      <p><strong>Alterado por:</strong> ${p.nomeAdministradorUltimaAlteracao || 'Desconhecido'}</p>
      <div class="comentario-section">
        <textarea placeholder="Escreva um comentário..."></textarea>
        <button class="btn-enviar" data-id="${p.id}">Enviar</button>
        <div class="comentarios"></div>
      </div>
    </div>
  `;

    container.appendChild(linha);

    const detalhes = linha.querySelector('.detalhes-expandido');
    const botao = linha.querySelector('.botao-detalhes');
    
    botao.addEventListener('click', () => {
      detalhes.classList.toggle('ativo');
      botao.textContent = detalhes.classList.contains('ativo') ? 'Ocultar' : 'Detalhes';
    });

    const statusSelect = linha.querySelector('.status-dropdown');
    statusSelect.value = p.status;
    statusSelect.addEventListener('change', async () => {
      const novoStatus = statusSelect.value;
      const usuario = JSON.parse(localStorage.getItem('usuarioLogado'));

      const atualizado = {
        ...p,
        status: novoStatus
      };

      await fetch(`${API_URL}/problemas/${p.id}/moderador/${usuario.id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(atualizado)
      });
    });

    const btnEnviar = linha.querySelector('.btn-enviar');
    const textarea = linha.querySelector('textarea');
    const containerComentarios = linha.querySelector('.comentarios');

    btnEnviar.addEventListener('click', async () => {
      const texto = textarea.value.trim();
      if (!texto) return;

      const usuario = JSON.parse(localStorage.getItem('usuarioLogado'));

      const comentario = {
        texto,
        problemaId: p.id,
        administradorId: usuario.id
      };

      await fetch(`${API_URL}/comentarios`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(comentario)
      });

      textarea.value = '';
      carregarComentarios(p.id, containerComentarios);
    });

    carregarComentarios(p.id, containerComentarios);
  });
}

function formatarData(dataStr) {
  if (!dataStr) return '-';
  return new Date(dataStr).toLocaleString('pt-BR', {
    day: '2-digit',
    month: '2-digit',
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit',
  });
}

async function carregarComentarios(problemaId, container) {
  container.innerHTML = '';
  const res = await fetch(`${API_URL}/comentarios/problema/${problemaId}`);
  const comentarios = await res.json();

  comentarios.forEach(c => {
    const div = document.createElement('div');
    div.className = 'comentario';
    div.innerHTML = `
      <strong>${c.administrador?.nome || 'Moderador'}:</strong>
      <p>${c.texto}</p>
    `;
    container.appendChild(div);
  });
}

function logout() {
  localStorage.removeItem('usuarioLogado');
  window.location.href = 'login.html';
}

document.getElementById('btnRelatorio').addEventListener('click', () => {
  window.location.href = 'relatorio-alteracoes.html';
});

document.getElementById('gerenciar-usuarios').addEventListener('click', () => {
  window.location.href = 'gerenciar-usuarios.html';
});
