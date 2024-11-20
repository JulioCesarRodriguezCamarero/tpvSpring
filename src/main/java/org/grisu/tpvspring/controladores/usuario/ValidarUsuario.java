package org.grisu.tpvspring.controladores.usuario;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ValidarUsuario implements Initializable {
    @FXML
    private AnchorPane anchorUsuario;

    @FXML
    private Button btnCrearUsuario;

    @FXML
    private Button btnListarUsuario;

    @FXML
    private Button btnSalir;

    @FXML
    private StackPane stackUsuario;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}


