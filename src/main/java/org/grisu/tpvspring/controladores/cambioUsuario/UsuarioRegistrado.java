package org.grisu.tpvspring.controladores.cambioUsuario;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import org.grisu.tpvspring.modelo.CajaPrincipal;
import org.grisu.tpvspring.modelo.Usuario;
import org.grisu.tpvspring.servicio.ICajaPrincipalServicio;
import org.grisu.tpvspring.servicio.IUsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class UsuarioRegistrado implements Initializable {
    @FXML
    private AnchorPane anchorUsuariosRegistrados;
    @Autowired
    private IUsuarioServicio servicio;
    @Autowired
    private ICajaPrincipalServicio cajaPrincipalServicio;
    private final ObservableList<Usuario> usuariosObservable = FXCollections.observableArrayList();
    public static Usuario miUsuario;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (usuariosObservable.isEmpty()) {
            usuariosObservable.addAll(servicio.listar());
        }
        //todo  Crear un FlowPane para organizar los botones en filas y columnas
        FlowPane flowPane = new FlowPane();
        flowPane.setPrefWidth(700);

        flowPane.setHgap(25); // Espacio horizontal entre los botones
        flowPane.setVgap(15); // Espacio vertical entre los botones
        flowPane.setStyle("-fx-padding: 10;");

        usuariosObservable.forEach(usuario -> {
            Button btn = new Button(usuario.getNombre());
            btn.setStyle("-fx-background-color: #5DF0AD; -fx-padding: 3; -fx-pref-height: 50; -fx-pref-width: 150;");
            btn.setOnMouseClicked(this::eventoBoton);
            flowPane.getChildren().add(btn);
        });

        anchorUsuariosRegistrados.getChildren().add(flowPane);
    }

    private void eventoBoton(MouseEvent event) {
        CajaPrincipal cajaPrincipal = cajaPrincipalServicio.listar().getLast();
        if (event.getSource() instanceof Button btn) {
            usuariosObservable.forEach(usuario -> {
                usuario.setActivo(false);
                servicio.agregar(usuario);
            });
            miUsuario = servicio.buscar(btn.getText());
            miUsuario.setActivo(true);
            miUsuario.setCajaPrincipal(cajaPrincipal);
            servicio.agregar(miUsuario);
            btn.getScene().getWindow().hide();

        }
    }


}
