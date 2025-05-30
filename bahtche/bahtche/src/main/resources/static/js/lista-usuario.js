const API_URL = "http://52.71.87.155";

document.addEventListener('DOMContentLoaded', async () => {
  const usuario = JSON.parse(localStorage.getItem('usuarioLogado'));
  if (!usuario) return redirecionarParaLogin();

  await carregarReportesDoUsuario(usuario.id);
  configurarModal(usuario);
});

function redirecionarParaLogin() {
  alert('Usuário não logado. Redirecionando...');
  window.location.href = 'login.html';
}

async function carregarReportesDoUsuario(usuarioId) {
  try {
    const response = await fetch(`${API_URL}/problemas`);
    const todos = await response.json();
    const meus = todos.filter(p => p.usuarioId === usuarioId);
    renderizarLista(meus);
  } catch (erro) {
    console.error('Erro ao carregar reportes:', erro);
  }
}

function renderizarLista(reportes) {
  const container = document.getElementById('tabelaUsuarioProblemas');
  container.innerHTML = '';

  reportes.forEach(p => {
    const card = document.createElement('div');
    card.className = 'linha';
    card.innerHTML = `
      <h3>${p.titulo}</h3>
      <p><strong>Descrição:</strong> ${p.descricao}</p>
      <p><strong>Categoria:</strong> ${p.categoria}</p>
      <p><strong>Status:</strong> ${p.status}</p>
      <p><strong>Data de Criação:</strong> ${formatarData(p.dataCriacao)}</p>
      <p><strong>Endereço:</strong> 
        ${p.endereco?.rua || '-'}, Nº ${p.endereco?.numero || '-'},
        ${p.endereco?.bairro || '-'}, ${p.endereco?.cidade || '-'},
        CEP: ${p.endereco?.cep || '-'}
      </p>
      <button class="botao-comentarios">Comentários</button>
      <div class="comentarios-container" style="display: none;"></div>
    `;

    container.appendChild(card);

    const btnComentarios = card.querySelector('.botao-comentarios');
    const comentariosBox = card.querySelector('.comentarios-container');

    btnComentarios.addEventListener('click', async () => {
      const visivel = comentariosBox.style.display === 'block';
      comentariosBox.style.display = visivel ? 'none' : 'block';
      btnComentarios.textContent = visivel ? 'Comentários' : 'Ocultar';
      if (!visivel) await carregarComentarios(p.id, comentariosBox);
    });
  });
}

async function carregarComentarios(problemaId, container) {
  container.innerHTML = '<p>Carregando comentários...</p>';
  try {
    const res = await fetch(`${API_URL}/comentarios/problema/${problemaId}`);
    const comentarios = await res.json();

    const html = comentarios.map(c => `
      <div class="comentario">
        <strong>${c.administrador?.nome || 'Moderador'}:</strong>
        <p>${c.texto}</p>
      </div>
    `).join('');

    container.innerHTML = html || '<p>Sem comentários ainda.</p>';
  } catch (err) {
    container.innerHTML = '<p>Erro ao carregar comentários.</p>';
  }
}

function configurarModal(usuario) {
  const abrir = document.getElementById('abrirModal');
  const fechar = document.getElementById('fecharModal');
  const overlay = document.getElementById('modalOverlay');
  const form = document.getElementById('formNovoReporte');

  abrir?.addEventListener('click', () => overlay.style.display = 'flex');
  fechar?.addEventListener('click', () => overlay.style.display = 'none');

  form?.addEventListener('submit', async (e) => {
    e.preventDefault();
    const dados = obterDadosDoFormulario();
    if (!validarCampos(dados)) {
      alert('Preencha todos os campos.');
      return;
    }

    const novoReporte = montarObjetoDeReporte(dados, usuario.id);
    await enviarReporte(novoReporte);
    overlay.style.display = 'none';
    location.reload();
  });
}

function obterDadosDoFormulario() {
  const campos = ['titulo', 'descricao', 'categoria', 'bairro', 'rua', 'numero', 'cidade', 'cep'];
  return campos.reduce((obj, campo) => {
    obj[campo] = document.getElementById(campo)?.value.trim() || '';
    return obj;
  }, {});
}

function validarCampos(dados) {
  return Object.values(dados).every(valor => valor.length > 0);
}

function montarObjetoDeReporte(dados, usuarioId) {
  return {
    titulo: dados.titulo,
    descricao: dados.descricao,
    categoria: dados.categoria,
    status: 'PENDENTE',
    endereco: {
      bairro: dados.bairro,
      rua: dados.rua,
      numero: dados.numero,
      cidade: dados.cidade,
      estado: 'SP',
      cep: dados.cep
    },
    usuario: { id: usuarioId }
  };
}

async function enviarReporte(reporte) {
  await fetch(`${API_URL}/problemas`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(reporte)
  });
}

function formatarData(dataStr) {
  if (!dataStr) return '-';
  const data = new Date(dataStr);
  return isNaN(data.getTime()) ? '-' : data.toLocaleDateString();
}

function logout() {
  localStorage.removeItem('usuarioLogado');
  window.location.href = 'login.html';
}
