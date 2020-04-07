package br.infnet.edu.gui.GUIController.DTO.Cotacoes;

import javafx.beans.property.*;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class CotacaoDTO {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty preco = new SimpleStringProperty();
    private StringProperty data = new SimpleStringProperty();
    private IntegerProperty id_produto = new SimpleIntegerProperty();
    private StringProperty nome = new SimpleStringProperty();
    private StringProperty fornecedor = new SimpleStringProperty();

    public static Locale LOCALE = new Locale("pt", "BR");
    public static SimpleDateFormat DATE = new SimpleDateFormat("dd/MM/yyyy");
    public static NumberFormat CURRENCY = NumberFormat.getCurrencyInstance(LOCALE);

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

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getId_produto() {
        return id_produto.get();
    }

    public IntegerProperty id_produtoProperty() {
        return id_produto;
    }

    public void setId_produto(int id_produto) {
        this.id_produto.set(id_produto);
    }

    public String getPreco() {
        return preco.get();
    }

    public StringProperty precoProperty() {
        return preco;
    }

    public void setPreco(double preco) {
        var _preco = CURRENCY.format(preco);
        this.preco.set(_preco);
    }

    public String getData() {
        return data.get();
    }

    public StringProperty dataProperty() {
        return data;
    }

    public void setData(Date data) {
        var _data = DATE.format(data);
        this.data.set(_data);
    }
}
