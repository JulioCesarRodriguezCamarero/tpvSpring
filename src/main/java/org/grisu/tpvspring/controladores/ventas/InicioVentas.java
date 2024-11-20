package org.grisu.tpvspring.controladores.ventas;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.grisu.tpvspring.SpringContext;
import org.grisu.tpvspring.controladores.Index;
import org.grisu.tpvspring.controladores.caja.Caja;
import org.grisu.tpvspring.modelo.CajaPrincipal;
import org.grisu.tpvspring.modelo.Cuenta;
import org.grisu.tpvspring.modelo.Usuario;
import org.grisu.tpvspring.servicio.ICajaPrincipalServicio;
import org.grisu.tpvspring.servicio.ICuentaServicio;
import org.grisu.tpvspring.servicio.IUsuarioServicio;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Stream;

@Component
public class InicioVentas implements Initializable {

    @FXML
    private Button btnAgregarCuenta;

    @FXML
    private Button btnEntradaCaja;

    @FXML
    private Button btnPrincipal;

    @FXML
    private Button btnSalidaCaja;
    @FXML
    private AnchorPane anchorVentas;
    @FXML
    private Pane paneCuentas;

    @Autowired
    private ICuentaServicio servicioCuenta;
    @Autowired
    private IUsuarioServicio servicioUsuario;
    @Autowired
    private ICajaPrincipalServicio servicioPrincipal;
    private CajaPrincipal cajaPrincipal;
    private Cuenta cuenta;
    private Usuario usuario;


    private final ObservableList<Cuenta> cuentasObservable = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (cuentasObservable.isEmpty()) {
            cuentasObservable.addAll(servicioCuenta.listar());
            cuentasObservable.forEach(cuenta -> {
                cuenta.setActivo(false);
                servicioCuenta.agregar(cuenta);
            });
        }

        //todo  Crear un FlowPane para organizar los botones en filas y columnas
        FlowPane flowPane = new FlowPane();
        flowPane.setPrefWidth(700);

        flowPane.setHgap(25); // Espacio horizontal entre los botones
        flowPane.setVgap(15); // Espacio vertical entre los botones
        flowPane.setStyle("-fx-padding: 10;");

        for (Cuenta cuenta : cuentasObservable) {
            Button boton = new Button(cuenta.getNombre());
            boton.setStyle("-fx-background-color: #5DF0AD; -fx-padding: 3; -fx-pref-height: 50; -fx-pref-width: 150;");
            if (cuenta.isActivo()) {
                boton.setStyle("-fx-background-color: red; -fx-padding: 3; -fx-pref-height: 50; -fx-pref-width: 150;");
            }

            boton.setOnMouseClicked(this::eventoBoton);
            flowPane.getChildren().add(boton);
        }
        paneCuentas.getChildren().add(flowPane);

        btnPrincipal.setOnAction(e -> {
            FXMLLoader loader = new FXMLLoader(Index.class.getResource("/templates/index.fxml"));
            loader.setControllerFactory(SpringContext.getBeanFactory());
            Stage stage = new Stage(StageStyle.UTILITY);
            Scene scene = null;
            try {
                scene = new Scene(loader.load());
                stage.setTitle("TPV");
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setMaximized(true);
                stage.setScene(scene);
                anchorVentas.getScene().getWindow().hide();
                stage.show();

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });

    }

    protected void eventoBoton(MouseEvent event) {

        usuario = servicioUsuario.buscarActivoConCuentas(true);
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuario);
        if (event.getSource() instanceof Button btn) {
            cuenta = servicioCuenta.buscarPorNombre(btn.getText());
            cuenta.setActivo(true);
            cuenta.setUsuarioList(usuarios);
            servicioCuenta.agregar(cuenta);
        }

        FXMLLoader loader = new FXMLLoader(Index.class.getResource("/templates/ventas/ventas.fxml"));
        loader.setControllerFactory(SpringContext.getBeanFactory());
        Stage stage = new Stage(StageStyle.UTILITY);
        Scene scene;
        try {
            scene = new Scene(loader.load());
            stage.setTitle("Ventas");
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setMaximized(true);
            Ventas botonesVentas = loader.getController();
            botonesVentas.nombreBoton(event);
            stage.show();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


}
