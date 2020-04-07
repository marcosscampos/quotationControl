package br.infnet.edu.gui.MainGUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ViewGUI/view.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Sistema de Cotações");
            stage.show();

        } catch (Exception ex) {

            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERRO");
            alert.setContentText("ERRO! Não foi possível iniciar o sistema.");
        }
    }
}
