package br.infnet.edu.gui.GUIController;

import br.infnet.edu.cotacao.Cotacao;
import br.infnet.edu.cotacao.CotacaoDAO;
import br.infnet.edu.gui.GUIController.DTO.Cotacoes.CotacaoDTO;
import br.infnet.edu.gui.GUIController.DTO.Produtos.ProdutoDTO;
import br.infnet.edu.produtos.Produto;
import br.infnet.edu.produtos.ProdutoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class QuotationController implements Initializable {
    ProdutoDAO produtodao = new ProdutoDAO();
    CotacaoDAO cotacaodao = new CotacaoDAO();

    private List<Produto> listaProduto = new ArrayList<>();

    @FXML
    private TableView<ProdutoDTO> tableProduto;

    @FXML
    private TableView<CotacaoDTO> tableCotacao;

    @FXML
    private TableColumn<CotacaoDTO, Integer> tableCotacaoId;

    @FXML
    private TableColumn<CotacaoDTO, String> tableCotacaoProduto;

    @FXML
    private TableColumn<CotacaoDTO, String> tableCotacaoPreco;

    @FXML
    private TableColumn<CotacaoDTO, String> tableCotacaoFornecedor;

    @FXML
    private TableColumn<CotacaoDTO, String> tableCotacaoData;

    @FXML
    private TableColumn<ProdutoDTO, Integer> tableProdutoId;

    @FXML
    private TableColumn<ProdutoDTO, String> tableProdutoNome;

    @FXML
    private TableColumn<ProdutoDTO, String> tableProdutoFornecedor;

    @FXML
    private Button btnSalvarProduto;

    @FXML
    private Button btnExcluir;

    @FXML
    private TextField txtProduto;

    @FXML
    private TextField txtFornecedor;

    @FXML
    private TextField txtpreco;

    @FXML
    private DatePicker dtCotacaoData;

    @FXML
    private Button btnSalvarCotacao;

    @FXML
    private Button btnExcluirCotacao;

    @FXML
    private ComboBox<Produto> comboFiltro;

    @FXML
    private Button btnPesquisar;

    @FXML
    private ComboBox<Cotacao> dropfiltro;

    @FXML
    private ComboBox<Produto> dropProduto;

    @FXML
    private Button btnExportar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Data - DatePicker
        String pattern = "dd/MM/yyyy";
        dtCotacaoData.setShowWeekNumbers(true);
        dtCotacaoData.setPromptText(pattern.toLowerCase());
        dtCotacaoData.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate localDate) {
                if (localDate != null) {
                    return dateTimeFormatter.format(localDate);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String s) {
                if (s != null && !s.isEmpty()) {
                    return LocalDate.parse(s, dateTimeFormatter);
                } else {
                    return null;
                }
            }
        });

        //Produtos
        definirColunasProduto();
        editarProduto();
        var produtos = carregarTableViewProdutos();
        tableProduto.setItems(produtos);

        btnSalvarProduto.setOnAction(e -> {
            try {
                cadastrarProduto();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setHeaderText("");
                alert.setContentText("Produto cadastrado com sucesso!");

            } catch (Exception ex) {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERRO!");
                alert.setHeaderText("");
                alert.setContentText("ERRO! Não foi possível cadastrar um produto.");
            }
        });

        //Cotações
        definirColunasCotacao();
        editarCotacao();
        var cotacoes = carregarTableViewCotacao();
        var produto = FXCollections.observableArrayList(produtodao.listar());
        tableCotacao.setItems(cotacoes);

        btnExportar.setOnAction(e -> {
            exportarExcel();
        });

        btnExcluirCotacao.setOnAction(e -> {
            SelectionModel selectionModel = tableCotacao.getSelectionModel();
            var itemSelecionado = selectionModel.getSelectedItem();
            cotacaodao.excluir((Cotacao) itemSelecionado);
        });

        btnSalvarCotacao.setOnAction(e -> {
            try {
                cadastrarCotacao();

            } catch (Exception ex) {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("");
                alert.setTitle("ERRO!");
                alert.setContentText("ERRO! Não foi possivel cadastrar uma cotação.");
            }
        });
        dropProduto.setItems(produto);
        dropProduto.setConverter(new ConverterProduto());
    }

    public ObservableList<ProdutoDTO> carregarTableViewProdutos() {
        var produtos = produtodao.listar();

        ObservableList<ProdutoDTO> result = FXCollections.observableArrayList();

        if (produtos != null) {
            for (Produto produto : produtos) {
                var dto = new ProdutoDTO();
                dto.setId(produto.getId());
                dto.setNome(produto.getNome());
                dto.setFornecedor(produto.getFornecedor());
                result.add(dto);
            }
        }

        return result;
    }

    public ObservableList<CotacaoDTO> carregarTableViewCotacao() {
        var cotacoes = cotacaodao.listar();

        ObservableList<CotacaoDTO> result = FXCollections.observableArrayList();

        if (cotacoes != null) {
            for (Cotacao cotacao : cotacoes) {
                var dto = new CotacaoDTO();
                dto.setId(cotacao.getId());
                dto.setPreco(cotacao.getPreco());
                dto.setData(cotacao.getData());
                dto.setNome(cotacao.getProduto().getNome());
                dto.setFornecedor(cotacao.getProduto().getFornecedor());
                result.add(dto);
            }
        }

        return result;
    }

    private void definirColunasProduto() {
        tableProdutoId.setCellValueFactory(param -> param.getValue().idProperty().asObject());
        tableProdutoNome.setCellValueFactory(param -> param.getValue().nomeProperty());
        tableProdutoFornecedor.setCellValueFactory(param -> param.getValue().fornecedorProperty());
    }

    private void definirColunasCotacao() {
        tableCotacaoId.setCellValueFactory(param -> param.getValue().idProperty().asObject());
        tableCotacaoProduto.setCellValueFactory(param -> param.getValue().nomeProperty());
        tableCotacaoFornecedor.setCellValueFactory(param -> param.getValue().fornecedorProperty());
        tableCotacaoPreco.setCellValueFactory(param -> param.getValue().precoProperty());
        tableCotacaoData.setCellValueFactory(param -> param.getValue().dataProperty());
    }

    private void editarProduto() {
        tableProdutoNome.setCellFactory(TextFieldTableCell.forTableColumn());
        tableProdutoFornecedor.setCellFactory(TextFieldTableCell.forTableColumn());
        produtodao.listar();
    }

    private void editarCotacao() {
        tableCotacaoProduto.setCellFactory(TextFieldTableCell.forTableColumn());
        tableCotacaoFornecedor.setCellFactory(TextFieldTableCell.forTableColumn());
        tableCotacaoData.setCellFactory(TextFieldTableCell.forTableColumn());
        cotacaodao.listar();
    }

    private void exportarExcel() {
        Writer writer;
        String pasta = System.getProperty("user.home");
        String arquivo = "/Cotacao.csv";

        var cotacoes = cotacaodao.listar();

        ObservableList<CotacaoDTO> result = FXCollections.observableArrayList();

        if (cotacoes != null) {
            for (Cotacao cotacao : cotacoes) {
                var dto = new CotacaoDTO();
                dto.setId(cotacao.getId());
                dto.setPreco(cotacao.getPreco());
                dto.setData(cotacao.getData());
                dto.setNome(cotacao.getProduto().getNome());
                dto.setFornecedor(cotacao.getProduto().getFornecedor());
                result.add(dto);
            }
        }

        try {
            File file = new File(pasta + arquivo);
            writer = new BufferedWriter(new FileWriter(file));
            for (CotacaoDTO cotacao : result) {
                String texto = "ID: " + cotacao.getId() + ";"
                        + "Produto: " + cotacao.getNome() + ";"
                        + "Preço: " + cotacao.getPreco() + ";"
                        + "Data Cotacao: " + cotacao.getData() + "\n";

                writer.write(texto);
            }

            writer.flush();
            writer.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("");
            alert.setContentText("Exportado com sucesso!");
            alert.show();

        } catch (Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRO!");
            alert.setHeaderText("");
            alert.setContentText("ERRO! Não foi possível exportar.");
            alert.show();
        }
    }

    private void cadastrarCotacao() {
        Cotacao cotacao = new Cotacao();
        var produtoId = dropProduto.getSelectionModel().getSelectedIndex();
        var produto = produtodao.obterPor(produtoId);
        var date = Instant.from(dtCotacaoData.getValue().atStartOfDay(ZoneId.systemDefault()));
        var preco = txtpreco.getText();

        cotacao.setProduto(produto);
        cotacao.setData(Date.from(date));
        cotacao.setPreco(Double.parseDouble(preco));
        cotacaodao.inserir(cotacao);
    }

    private void cadastrarProduto() {
        try {
            Produto produto = new Produto();
            produto.setNome(txtProduto.getText());
            produto.setFornecedor(txtFornecedor.getText());

            produtodao.inserir(produto);

            txtProduto.setText("");
            txtFornecedor.setText("");
        } catch (Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRO!");
            alert.setHeaderText("");
            alert.setContentText("ERRO! Não foi possível cadastrar um produto.");
        }
    }

    private class ConverterProduto extends StringConverter<Produto> {

        @Override
        public String toString(Produto produto) {
            if(produto != null) {
                return produto.getNome();
            }

            return null;
        }

        @Override
        public Produto fromString(String s) {
            return produtodao.listar().stream()
                    .filter(p ->  p.getNome().equals(s))
                    .findFirst()
                    .orElse(null);
        }
    }
}
