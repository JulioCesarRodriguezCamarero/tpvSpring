package org.grisu.tpvspring.controladores.administracion.cuenta;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.grisu.tpvspring.SpringContext;
import org.grisu.tpvspring.modelo.Cuenta;
import org.grisu.tpvspring.servicio.ICuentaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ListadoCuentas implements Initializable {
    @FXML
    private AnchorPane anchorListarCuentas;

    @FXML
    private TableColumn<Cuenta, String> columCuenta;

    @FXML
    private TableColumn<Cuenta,Long> columId;

    @FXML
    private TableView<Cuenta> tablaListarCuentas;
    @Autowired
    private ICuentaServicio servicio;
    private final ObservableList<Cuenta> observableList = FXCollections.observableArrayList();
    public static Long miId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableList.clear();
        observableList.addAll(servicio.listar());
        if (observableList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Introduce cuentas ");
            alert.setContentText("Tabla vacía, no hay cuentas");
            alert.showAndWait();
            return;
        }
        tablaListarCuentas.setItems(observableList);
        tablaListarCuentas.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        columId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columCuenta.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        //todo                                        BUSCAR CUENTA POR ID
        tablaListarCuentas.setOnMouseClicked(event -> {

            miId =tablaListarCuentas.getSelectionModel().getSelectedItem().getId();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/templates/administracion/cuentas/cuentaSeleccionada.fxml"));
            loader.setControllerFactory(SpringContext.getBeanFactory());


            try {
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Cuenta Seleccionada");
                stage.show();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}
