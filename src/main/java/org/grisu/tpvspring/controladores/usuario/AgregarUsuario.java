package org.grisu.tpvspring.controladores.usuario;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.grisu.tpvspring.modelo.CajaPrincipal;
import org.grisu.tpvspring.modelo.Usuario;
import org.grisu.tpvspring.servicio.ICajaPrincipalServicio;
import org.grisu.tpvspring.servicio.IUsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class AgregarUsuario implements Initializable {
    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnCancelar;

    @FXML
    private TextField txtUsuario;
    @Autowired
    private IUsuarioServicio servicioUsuario;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnAgregar.setOnAction(e -> {
            Usuario usuario = new Usuario();
            usuario.setNombre(txtUsuario.getText());
            usuario.setActivo(false);
            servicioUsuario.agregar(usuario);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Usuario agregado");
            alert.setContentText("Usuario agregado con éxito");
            alert.showAndWait();
            txtUsuario.setText("");
        });
        btnCancelar.setOnAction(e -> {
            btnCancelar.getScene().getWindow().hide();
        });
    }
}
