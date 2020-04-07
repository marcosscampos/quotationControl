package br.infnet.edu.gui.GUIController.DTO.Produtos;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ProdutoDTO {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty nome = new SimpleStringProperty();
    private StringProperty fornecedor = new SimpleStringProperty();

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getNome() {
        return nome.get();
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public String getFornecedor() {
        return fornecedor.get();
    }

    public StringProperty fornecedorProperty() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor.set(fornecedor);
    }
}
