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

import static org.grisu.tpvspring.controladores.administracion.cuenta.ListadoCuentas.miId;

@Component
public class CuentaSeleccionada implements Initializable {

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
    private Cuenta cuenta;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
              // todo buscar cuenta
        cuenta = servicio.buscar(miId);
        textDescripcion.setText(cuenta.getNombre());

        // todo actualizar cuenta
        btnAgregar.setOnAction(event -> {
            cuenta.setNombre(textDescripcion.getText());
            servicio.agregar(cuenta);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Actualización");
            alert.setContentText("Cuenta actualizada con éxito");
            alert.showAndWait();
        });
        btnCancelar.setOnAction(event->{
            Stage stage = (Stage) btnCancelar.getScene().getWindow();
            stage.close();
        });

    }
}
