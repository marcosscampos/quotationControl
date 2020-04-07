package br.infnet.edu.cotacao;

import br.infnet.edu.jdbc.ConnectionFactory;
import br.infnet.edu.produtos.Produto;
import javafx.scene.control.Alert;

import javax.xml.transform.Result;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CotacaoDAO {

    public Cotacao inserir(Cotacao produto) {

        try (Connection con = ConnectionFactory.conectar()) {
            String sql = "INSERT INTO cotacao(preco, data, id_produto) VALUES(?, ?, ?)";

            try(PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                System.out.println(produto.getProduto().getId());
                ps.setDouble(1, produto.getPreco());
                ps.setDate(2, new Date(produto.getData().getTime()));
                ps.setInt(3, produto.getProduto().getId());
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if(rs != null && rs.next()) {
                    return obterPor(rs.getInt(1));
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRO!");
            alert.setHeaderText("");
            alert.setContentText("ERRO: Não foi possível concluir a operação.");
            alert.show();
        }
        return produto;
    }

    public List<Cotacao> listar() {
        List<Cotacao> cotacoes = new ArrayList<>();
        ResultSet rs;

        try (Connection con = ConnectionFactory.conectar()) {
            String sql = "SELECT cotacao.id, cotacao.preco , cotacao.data, cotacao.id_produto," +
                    " produto.nome, produto.fornecedor FROM cotacao  JOIN produto ON cotacao.id_produto = produto.id";

            Statement statement = con.createStatement();
            rs = statement.executeQuery(sql);
            while(rs.next()){
                cotacoes.add(obterCotacao(rs));
            }

            rs.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRO!");
            alert.setHeaderText("");
            alert.setContentText("ERRO: Não foi possível concluir a operação.");
            alert.show();
        }
        return cotacoes;
    }

    public List<Cotacao> listarPor(Produto nome) {
        List<Cotacao> cotacoes = new ArrayList<>();
        ResultSet rs;

        try (Connection con = ConnectionFactory.conectar()) {
            String sql = "SELECT cotacao.id, cotacao.preco, cotacao.data, cotacao.id_produto, produto.nome," +
                    " produto.fornecedor FROM cotacao " +
                    "JOIN produto on cotacao.id_produto = produto.id WHERE produto.nome = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nome.getNome());

            rs = ps.executeQuery();

            while(rs.next()) {
                cotacoes.add(obterCotacao(rs));
            }

            rs.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRO!");
            alert.setHeaderText("");
            alert.setContentText("ERRO: Não foi possível concluir a operação.");
            alert.show();
        }

        return cotacoes;
    }

    public void excluir(Cotacao cotacao) {
        try (Connection con = ConnectionFactory.conectar()) {
            String sql = "DELETE FROM cotacao where id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, cotacao.getId());
            ps.executeUpdate();
            ps.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRO!");
            alert.setHeaderText("");
            alert.setContentText("ERRO: Não foi possível concluir a operação.");
            alert.show();
        }
    }

    public Cotacao alterar(Cotacao cotacao) {
        try (Connection con = ConnectionFactory.conectar()) {
            String sql = "UPDATE cotacao set cotacao.preco = ?, cotacao.data = ?, id_produto = ? WHERE id = ?";

            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, cotacao.getPreco());
            ps.setDate(2, (Date) cotacao.getData());
            ps.setInt(3, cotacao.getProduto().getId());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs != null && rs.next()) {
                return obterPor(rs.getInt(0));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRO!");
            alert.setHeaderText("");
            alert.setContentText("ERRO: Não foi possível concluir a operação.");
            alert.show();
        }
        return cotacao;
    }

    public Cotacao obterPor(int id) {
        Cotacao cotacao = new Cotacao();

        try (Connection con = ConnectionFactory.conectar()) {
            String sql = "SELECT cotacao.id, cotacao.preco, data, id_produto," +
                    " produto.nome, produto.fornecedor FROM cotacao" +
                    " JOIN  produto on cotacao.id_produto = produto.id" +
                    " WHERE cotacao.id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if(rs != null && rs.next()) {
                return obterCotacao(rs);
            }

            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRO!");
            alert.setHeaderText("");
            alert.setContentText("ERRO: Não foi possível concluir a operação.");
            alert.show();
        }

        return cotacao;
    }

    private Cotacao obterCotacao(ResultSet retorno) {
        Produto produto = new Produto();
        Cotacao cotacao = new Cotacao();
        try {
            produto.setId(retorno.getInt("id_produto"));
            produto.setNome(retorno.getString("nome"));
            produto.setFornecedor(retorno.getString("fornecedor"));

            cotacao.setId(retorno.getInt("id"));
            cotacao.setPreco(retorno.getDouble("preco"));
            cotacao.setData(retorno.getDate("data"));
            cotacao.setProduto(produto);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return cotacao;
    }
}
