const http = require("http");

exports.handler = async (event) => {
  const url = "http://52.71.87.155/problemas";

  const fetchProblemas = () => {
    return new Promise((resolve, reject) => {
      http.get(url, (res) => {
        let data = "";
        res.on("data", (chunk) => (data += chunk));
        res.on("end", () => resolve(JSON.parse(data)));
      }).on("error", reject);
    });
  };

  const contarPorCampo = (lista, campo) => {
    return lista.reduce((acc, item) => {
      const chave = item[campo] || "Não informado";
      acc[chave] = (acc[chave] || 0) + 1;
      return acc;
    }, {});
  };

  try {
    const problemas = await fetchProblemas();

    const total = problemas.length;
    const porCategoria = contarPorCampo(problemas, "categoria");
    const porStatus = contarPorCampo(problemas, "status");
    const porBairro = contarPorCampo(problemas, "bairro");

    return {
      statusCode: 200,
      body: JSON.stringify({
        total_reportes: total,
        por_categoria: porCategoria,
        por_status: porStatus,
        por_bairro: porBairro,
      }),
    };
  } catch (error) {
    console.error("Erro ao buscar dados:", error);
    return {
      statusCode: 500,
      body: JSON.stringify({ error: "Erro ao processar estatísticas" }),
    };
  }
};
