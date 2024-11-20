package org.grisu.tpvspring.controladores;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.grisu.tpvspring.SpringContext;
import org.grisu.tpvspring.controladores.administracion.Administracion;
import org.grisu.tpvspring.controladores.usuario.ValidarUsuario;
import org.grisu.tpvspring.controladores.ventas.InicioVentas;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class Index implements Initializable {
    @FXML
    private Button btnAdministracion;

    @FXML
    private Button btnCaja;

    @FXML
    private Button btnConfiguracion;

    @FXML
    private Button btnSalir;

    @FXML
    private Button btnCamboUsuario;

    @FXML
    private Button btnVentas;

    @FXML
    private StackPane panelCentral;
    @FXML
    private AnchorPane anchorPrincipal;
    private Stage miStage;
    private String ruta;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnSalir.setOnAction(event -> System.exit(0));

        btnCamboUsuario.setOnAction(event -> {
            ruta="/templates/cambioUsuario/usuarioRegistrado.fxml";
            pantallaPrincipal(ruta,btnCamboUsuario);
        });
        btnVentas.setOnAction(event -> {
            ruta="/templates/ventas/inicioVentas.fxml";
            pantallaPrincipal(ruta,btnVentas);

        });
        btnAdministracion.setOnAction(event -> {
           ruta="/templates/administracion/administracion.fxml";
            pantallaPrincipal(ruta,btnAdministracion);
        });
        btnConfiguracion.setOnAction(event -> {
            ruta="/templates/configuracion/configuracion.fxml";
            pantallaPrincipal(ruta,btnConfiguracion);
        });
        btnCaja.setOnAction(event -> {
        ruta="/templates/caja/caja.fxml";
        pantallaPrincipal(ruta,btnCaja);
        });
    }

    public void pantallaPrincipal(String ruta,Button boton) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
        loader.setControllerFactory(SpringContext.getBeanFactory());
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
         if (boton.getText().equals("PantallaVentas")) {
            stage.setMaximized(true);
            boton.getScene().getWindow().hide();
         }
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }
}
