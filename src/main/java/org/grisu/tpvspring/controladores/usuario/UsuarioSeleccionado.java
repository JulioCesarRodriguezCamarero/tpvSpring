package org.grisu.tpvspring.controladores.usuario;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.grisu.tpvspring.modelo.Usuario;
import org.grisu.tpvspring.servicio.IUsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

import static org.grisu.tpvspring.controladores.usuario.ListaUsuarios.idUsuario;

@Component
public class UsuarioSeleccionado implements Initializable {
    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnCancelar;

    @FXML
    private TextField txtUsuario;
    @Autowired
    private IUsuarioServicio servicio;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Usuario usuario = servicio.buscar(idUsuario);
        txtUsuario.setText(usuario.getNombre());
        btnActualizar.setOnAction(event -> {
            usuario.setNombre(txtUsuario.getText());
            servicio.agregar(usuario);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Actualización");
            alert.setContentText("Usuario actualizado con éxito");
            alert.showAndWait();
            txtUsuario.setText("");
        });
        btnCancelar.setOnAction(event -> {
           btnCancelar.getScene().getWindow().hide();
        });


    }


}
