package org.grisu.tpvspring.controladores.usuario;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.grisu.tpvspring.SpringContext;
import org.grisu.tpvspring.modelo.Usuario;
import org.grisu.tpvspring.servicio.IUsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ListaUsuarios implements Initializable {
    @FXML
    private TableColumn<Usuario, Long> columIdUsuario;

    @FXML
    private TableColumn<Usuario, String> columNombreUsuario;

    @FXML
    private TableView<Usuario> tablaUsuarios;

    private final ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();
    @Autowired
    private IUsuarioServicio servicio;
    private StackPane stackPane;
    public static long idUsuario;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listaUsuarios.clear();
        listaUsuarios.addAll(servicio.listar());
        if (listaUsuarios.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Introduce cuentas ");
            alert.setContentText("Tabla vacía, no hay cuentas");
            alert.showAndWait();
            return;
        }
        tablaUsuarios.setItems(listaUsuarios);
        tablaUsuarios.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        columIdUsuario.setCellValueFactory(new PropertyValueFactory<>("id"));
        columNombreUsuario.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        //todo          Buscar Usuario Por Id
        tablaUsuarios.setOnMouseClicked(event -> {
          idUsuario = tablaUsuarios.getSelectionModel().getSelectedItem().getId();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/templates/administracion/usuario/usuarioSeleccionado.fxml"));
            loader.setControllerFactory(SpringContext.getBeanFactory());
            stackPane.getChildren().clear();

            try {
                Parent root = loader.load();
                Stage stage = (Stage) stackPane.getScene().getWindow();
                stackPane.getChildren().add(root);
                stage.show();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }


    public void miControlador(StackPane stackUsuario) {
        this.stackPane = stackUsuario;
    }
}
