package org.grisu.tpvspring.controladores.administracion;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.grisu.tpvspring.SpringContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


@Component
public class Administracion implements Initializable {
    @FXML
    private AnchorPane anchorAdministracion;

    @FXML
    private Label labelCopiasSeguridad;

    @FXML
    private Label labelCuentas;

    @FXML
    private Label labelListados;

    @FXML
    private Label labelProductos;

    @FXML
    private Label labelUsuarios;

    @FXML
    private Label labelVentas;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelProductos.setOnMouseClicked((event) -> {
            String ruta="/templates/administracion/productos/productos.fxml";
            cargarPagina(ruta);
        });
        labelCuentas.setOnMouseClicked((event) -> {
            String ruta="/templates/administracion/cuentas/cuentas.fxml";
            cargarPagina(ruta);
        });
        labelUsuarios.setOnMouseClicked((event) -> {
            String ruta="/templates/administracion/usuario/usuarios.fxml";
            cargarPagina(ruta);
        });
    }

    private static void cargarPagina(String ruta) {
        FXMLLoader loader = new FXMLLoader(Administracion.class.getResource(ruta));
        loader.setControllerFactory(SpringContext.getBeanFactory());
        try {
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            System.out.println("Error al cargar pagina inicio");
            ex.printStackTrace(System.out);
        }
    }


}
