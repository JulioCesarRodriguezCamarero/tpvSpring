package org.grisu.tpvspring.controladores.administracion.cuenta;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.grisu.tpvspring.SpringContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class Cuentas implements Initializable {
    @FXML
    private Label labelListado;

    @FXML
    private Label labelNuevo;

    @FXML
    private StackPane stackPaneProductos;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelNuevo.setOnMouseClicked(mouseEvent -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/templates/administracion/cuentas/agregarCuenta.fxml"));
            miPantalla(loader, labelNuevo.getScene(), stackPaneProductos);
        });
        labelListado.setOnMouseClicked(mouseEvent -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/templates/administracion/cuentas/listadoCuentas.fxml"));
            miPantalla(loader, labelNuevo.getScene(), stackPaneProductos);
        });
    }
    public static void miPantalla(FXMLLoader loader, Scene scene, StackPane stackPaneProductos) {
        loader.setControllerFactory(SpringContext.getBeanFactory());
        stackPaneProductos.getChildren().clear();
        try {
            Parent root = loader.load();
            Stage stage = (Stage) scene.getWindow();
            stackPaneProductos.getChildren().add(root);
            stage.show();

        } catch (IOException ex) {
            System.out.println("Error al cargar pagina inicio");
            ex.printStackTrace(System.out);
        }
    }
}
