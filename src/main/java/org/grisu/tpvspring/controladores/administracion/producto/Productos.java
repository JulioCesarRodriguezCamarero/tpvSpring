package org.grisu.tpvspring.controladores.administracion.producto;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.grisu.tpvspring.SpringContext;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class Productos implements Initializable {

    @FXML
    private AnchorPane anchorProducto;

    @FXML
    private Button btnCrearProducto;

    @FXML
    private Button btnListarProducto;

    @FXML
    private Button btnSalir;

    @FXML
    private StackPane stackProducto;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnCrearProducto.setOnMouseClicked(mouseEvent -> {
            String ruta = "/templates/administracion/productos/agregarProducto.fxml";
            administraPaginas(ruta,btnCrearProducto);
        });
        btnListarProducto.setOnMouseClicked(mouseEvent -> {
            String ruta = "/templates/administracion/productos/listadoProductos.fxml";
            administraPaginas(ruta,btnListarProducto);
        });
        btnSalir.setOnMouseClicked(mouseEvent -> {
            btnSalir.getScene().getWindow().hide();
        });
    }


    private void administraPaginas(String ruta, Button boton) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
        loader.setControllerFactory(SpringContext::getBean);
        stackProducto.getChildren().clear();
        try {
            Parent root = loader.load();
            Stage stage = (Stage) boton.getScene().getWindow();
            stackProducto.getChildren().add(root);
            if (boton.getText().equals("Listar Productos")) {
                ListadoProductos controller = loader.getController();
                controller.miControlador(stackProducto);
            }

            stage.show();

        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}
