document.addEventListener("DOMContentLoaded", () => {
  const botaoTema = document.getElementById("botao-tema");
  const temaSalvo = localStorage.getItem("temaPreferido");

  if (temaSalvo === "escuro") {
    document.body.classList.add("modo-escuro");
  }

  botaoTema.addEventListener("click", () => {
    document.body.classList.toggle("modo-escuro");
    const temaAtual = document.body.classList.contains("modo-escuro") ? "escuro" : "claro";
    localStorage.setItem("temaPreferido", temaAtual);
  });
});