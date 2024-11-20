package org.grisu.tpvspring.controladores.administracion.cuenta;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.grisu.tpvspring.modelo.Cuenta;
import org.grisu.tpvspring.servicio.ICuentaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;
@Component
public class AgregarCuenta implements Initializable {
    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnCancelar;

    @FXML
    private ImageView imageViewImagen;

    @FXML
    private StackPane stackImagen;

    @FXML
    private TextField textDescripcion;
    @Autowired
    private ICuentaServicio servicio;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnAgregar.setOnAction(e -> {
           Cuenta cuenta = new Cuenta();
           cuenta.setNombre(textDescripcion.getText());
            servicio.agregar(cuenta);
            limpiar();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Agregar Cuenta");
            alert.setHeaderText("Información");
            alert.setContentText("Cuenta creada con éxito");
            alert.show();
        });
        btnCancelar.setOnAction(e -> {
            Stage stage = (Stage) btnCancelar.getScene().getWindow();
            stage.close();
        });
    }

    private void limpiar() {
        textDescripcion.clear();

    }

}
