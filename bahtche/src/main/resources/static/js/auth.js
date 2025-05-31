function verificarAutenticacao() {
  const usuarioLogado = JSON.parse(localStorage.getItem('usuarioLogado'));

  if (!usuarioLogado) {
    alert('Você precisa estar logado para acessar esta página.');
    window.location.href = 'login.html';
  }

  return usuarioLogado;
}

function verificarModerador() {
  const usuarioLogado = verificarAutenticacao();

  if (usuarioLogado.role !== 'MODERADOR') {
    alert('Acesso restrito para moderadores.');
    window.location.href = 'lista-usuario.html';
  }
}

function obterIdUsuarioComVerificacao() {
  const usuario = verificarAutenticacao(); 
  return usuario.id;
}

