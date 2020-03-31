package br.infnet.edu.cotacao;

import br.infnet.edu.produtos.Produto;
import br.infnet.edu.produtos.ProdutoDAO;

public class TesteMain {
    public static void main(String[] args) {
        CotacaoDAO dao = new CotacaoDAO();
        ProdutoDAO dao1 = new ProdutoDAO();
        Cotacao cotacao = new Cotacao();
        Produto produto = new Produto();

        for (Produto cont : dao1.listar()) {
            System.out.println(cont.getNome() + " " + cont.getFornecedor());
        }
    }
}
