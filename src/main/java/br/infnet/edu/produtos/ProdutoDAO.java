package br.infnet.edu.produtos;

import br.infnet.edu.jdbc.ConnectionFactory;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    public void inserir(Produto produto) {
        try (Connection con = ConnectionFactory.conectar()) {
            String sql = "INSERT INTO produto(nome, fornecedor) VALUES(?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, produto.getNome());
            ps.setString(2, produto.getFornecedor());
            ps.executeUpdate();
            ps.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRO!");
            alert.setContentText("ERRO: Não foi possível concluir a operação.");

            alert.showAndWait();
        }
    }

    public List<Produto> listar() {
        List<Produto> retorno = new ArrayList<>();

        try (Connection con = ConnectionFactory.conectar()) {
            Statement statement = con.createStatement();
            String sql = "SELECT id, nome, fornecedor FROM produto ORDER BY id";

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setFornecedor(rs.getString("fornecedor"));
                retorno.add(produto);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRO!");
            alert.setContentText("ERRO: Não foi possível concluir a operação.");

            alert.showAndWait();
        }

        return retorno;
    }

    public List<Produto> listarPor(String fornecedor) {
        List<Produto> retorno = new ArrayList<>();

        try (Connection con = ConnectionFactory.conectar()) {
            Statement statement = con.createStatement();
            String sql = "SELECT id, nome, fornecedor FROM produto WHERE fornecedor = ?";

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setFornecedor(rs.getString("fornecedor"));
                retorno.add(produto);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRO!");
            alert.setContentText("ERRO: Não foi possível concluir a operação.");

            alert.showAndWait();
        }

        return retorno;
    }

    public void excluir(Produto produto) {
        try (Connection con = ConnectionFactory.conectar()) {
            String sql = "DELETE FROM produto WHERE id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, produto.getId());
            ps.executeUpdate();
            ps.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRO!");
            alert.setContentText("ERRO: Não foi possível concluir a operação.");

            alert.showAndWait();
        }
    }

    public void alterar(Produto produto) {
        try (Connection con = ConnectionFactory.conectar()) {
            String sql = "UPDATE produto SET nome = ?, fornecedor = ? WHERE id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, produto.getNome());
            ps.setString(2, produto.getFornecedor());
            ps.setInt(3, produto.getId());
            ps.executeUpdate();
            ps.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRO!");
            alert.setContentText("ERRO: Não foi possível concluir a operação.");

            alert.showAndWait();
        }
    }

    public Produto obterPor(int id) {
        List<Produto> produtos = new ArrayList<>();
        Produto produto = new Produto();

        try (Connection con = ConnectionFactory.conectar()) {
            String sql = "SELECT nome, fornecedor FROM produto WHERE id = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                produto.setNome(rs.getString("nome"));
                produto.setFornecedor(rs.getString("fornecedor"));
                produtos.add(produto);
            }

            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRO!");
            alert.setContentText("ERRO: Não foi possível concluir a operação.");

            alert.showAndWait();

        }

        return produto;
    }

    public Produto obterPor(String nome) {
        List<Produto> produtos = new ArrayList<>();
        Produto produto = new Produto();

        try (Connection con = ConnectionFactory.conectar()) {
            String sql = "SELECT nome, fornecedor FROM produto WHERE nome = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, nome);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setFornecedor(rs.getString("fornecedor"));
                produtos.add(produto);
            }

            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRO!");
            alert.setContentText("ERRO: Não foi possível concluir a operação.");

            alert.showAndWait();
        }

        return produto;
    }
}
