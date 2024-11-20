package org.grisu.tpvspring.controladores.usuario;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.grisu.tpvspring.SpringContext;
import org.grisu.tpvspring.modelo.Usuario;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class UsuarioPrincipal implements Initializable {

    private final UsuarioSeleccionado usuarioSeleccionado;
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

    public UsuarioPrincipal(UsuarioSeleccionado usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnCrearUsuario.setOnMouseClicked(mouseEvent -> {
            String ruta = "/templates/administracion/usuario/agregarUsuario.fxml";
            administraPaginas(ruta,btnCrearUsuario);
        });
        btnListarUsuario.setOnMouseClicked(mouseEvent -> {
            String ruta = "/templates/administracion/usuario/listaUsuarios.fxml";
            administraPaginas(ruta,btnListarUsuario);
        });
        btnSalir.setOnMouseClicked(mouseEvent -> {
            btnSalir.getScene().getWindow().hide();
        });
    }

    private void administraPaginas(String ruta, Button boton) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
        loader.setControllerFactory(SpringContext::getBean);
        stackUsuario.getChildren().clear();
        try{
            Parent root = loader.load();
            Stage stage = (Stage) boton.getScene().getWindow();
            stackUsuario.getChildren().add(root);
          try{
              ListaUsuarios  controlador =  loader.getController();
              controlador.miControlador(stackUsuario);
          }catch (Exception e){
              System.out.println(e.getMessage());
              System.out.println("No se puede ejecutar el controlador");
          }

            stage.show();

        }catch (Exception e){
            e.printStackTrace(System.err);
        }
    }



}

