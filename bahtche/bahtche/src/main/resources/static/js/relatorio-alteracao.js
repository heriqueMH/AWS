const API_URL = "http://52.71.87.155";

verificarAutenticacao(true);

let todosLogs = [];

document.addEventListener('DOMContentLoaded', async () => {
  todosLogs = await carregarLogsAlteracoes();
  if (todosLogs.length > 0) {
    preencherSelects(todosLogs);
    configurarFiltros();

    document.getElementById('btnCSV').addEventListener('click', () => baixarCSV(todosLogs));
  }
});

function configurarFiltros() {
  ['filtroAdmin','filtroTipoAcao', 'filtroCategoria', 'filtroBairro', 'filtroData'].forEach(id => {
    document.getElementById(id).addEventListener('change', aplicarFiltros);
  });
}

function aplicarFiltros() {
  const admin = document.getElementById('filtroAdmin').value.toLowerCase();
  const categoria = document.getElementById('filtroCategoria').value.toLowerCase();
  const bairro = document.getElementById('filtroBairro').value.toLowerCase();
  const dataFiltro = document.getElementById('filtroData').value;
  const tipoAcao = document.getElementById('filtroTipoAcao').value.toUpperCase();

  const filtrados = todosLogs.filter(log => {
    const nomeAdmin = log.nomeAdministrador?.toLowerCase() || '';
    const tipo = log.categoriaProblema?.toLowerCase() || '';
    const bairroLog = log.bairro?.toLowerCase() || '';
    const dataAlteracao = log.dataAlteracao?.split('T')[0];
    const acao = log.tipoAcao?.toUpperCase() || '';

    return (
      (!admin || nomeAdmin === admin) &&
      (!categoria || tipo === categoria) &&
      (!bairro || bairroLog === bairro) &&
      (!dataFiltro || dataAlteracao === dataFiltro) &&
      (!tipoAcao || acao === tipoAcao)
    );
  });

  renderizarLogs(filtrados);
}

function preencherSelects(logs) {
  const admins = new Set(), categorias = new Set(), bairros = new Set(), tiposAcao = new Set();

  logs.forEach(log => {
    if (log.nomeAdministrador) admins.add(log.nomeAdministrador);
    if (log.categoriaProblema) categorias.add(log.categoriaProblema);
    if (log.bairro) bairros.add(log.bairro);
    if (log.tipoAcao) tiposAcao.add(log.tipoAcao);
  });

  preencherSelect('filtroAdmin', admins);
  preencherSelect('filtroCategoria', categorias);
  preencherSelect('filtroBairro', bairros);
}

function preencherSelect(id, valores) {
  const select = document.getElementById(id);
  valores.forEach(valor => {
    const opt = document.createElement('option');
    opt.value = valor;
    opt.textContent = valor;
    select.appendChild(opt);
  });
}

async function carregarLogsAlteracoes() {
  try {
    const response = await fetch(`${API_URL}/logs`);
    const logs = await response.json();
    renderizarLogs(logs);
    return logs;
  } catch (error) {
    console.error('Erro ao carregar logs:', error);
    alert('Erro ao carregar logs.');
    return [];
  }
}

function renderizarLogs(logs) {
  const lista = document.getElementById('lista-relatorio');
  lista.innerHTML = '';

  if (logs.length === 0) {
    lista.innerHTML = '<p>Não há alterações registradas.</p>';
    return;
  }

  logs.forEach(log => {
    const div = document.createElement('div');
    div.className = 'log-item';
    div.innerHTML = `
      <p><strong>ID:</strong> ${log.id}</p>
      ${log.nomeUsuarioCriador ? `<p><strong>Criador:</strong> ${log.nomeUsuarioCriador}</p>` : ''}
      ${log.dataCriacaoProblema ? `<p><strong>Informação de criação:</strong> ${new Date(log.dataCriacaoProblema).toLocaleString('pt-BR')}</p>` : ''}
      ${log.nomeAdministrador ? `<p><strong>Administrador:</strong> ${log.nomeAdministrador}</p>` : ''}
      ${log.tituloProblema ? `<p><strong>Título:</strong> ${log.tituloProblema}</p>` : ''}
      ${log.categoriaProblema ? `<p><strong>Categoria:</strong> ${log.categoriaProblema}</p>` : ''}
      ${log.enderecoCompleto ? `<p><strong>Endereço:</strong> ${log.enderecoCompleto}</p>` : ''}
      ${log.dataAlteracao ? `<p><strong>Alteração:</strong> ${new Date(log.dataAlteracao).toLocaleString('pt-BR')}</p>` : ''}
      ${log.descricao ? `<p><strong>Descrição:</strong> ${log.descricao}</p>` : ''}
      ${log.tipoAcao ? `<p><strong>Tipo de Ação:</strong> ${log.tipoAcao}</p>` : ''}
    `;
    lista.appendChild(div);
  });
}

function baixarCSV(logs) {
  const csv = logs.map(log => [
    log.id,
    `"${log.descricao?.replace(/"/g, '""') || ''}"`,
    log.dataAlteracao ? new Date(log.dataAlteracao).toLocaleDateString('pt-BR') : '',
    log.nomeAdministrador || '',
    log.nomeUsuarioCriador || '',
    log.tituloProblema || '',
    log.categoriaProblema || '',
    log.bairro || '',
    log.enderecoCompleto || ''
  ].join(','));

  const csvContent = [
    'ID,Descrição,Data,Administrador,Criador,Título,Categoria,Bairro,Endereço Completo',
    ...csv
  ].join('\n');

  const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
  const link = document.createElement('a');
  link.href = URL.createObjectURL(blob);
  link.download = 'relatorio-alteracoes.csv';
  link.click();
}

function logout() {
  localStorage.removeItem('usuarioLogado');
  window.location.href = 'login.html';
}

document.getElementById('lista-admin').addEventListener('click', () => {
  window.location.href = 'lista-admin.html';
});
