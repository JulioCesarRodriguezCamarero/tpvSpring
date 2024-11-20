package org.grisu.tpvspring.controladores.caja;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.grisu.tpvspring.SpringContext;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class Caja implements Initializable {


    @FXML
    private StackPane stackCaja;

    @FXML
    private Button btnApertura;

    @FXML
    private Button btnTiqueX;

    @FXML
    private Button btnTiqueZ;
    private String ruta;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnApertura.setOnAction(e -> {
            ruta = "/templates/caja/apertura.fxml";
            pantallaRuta(ruta,btnApertura);

        });
        btnTiqueX.setOnAction(e -> {
            ruta = "/templates/caja/tiqueX.fxml";
            pantallaRuta(ruta,btnTiqueX);
        });
        btnTiqueZ.setOnAction(e -> {
            ruta = "/templates/caja/tiqueZ.fxml";
            pantallaRuta(ruta,btnTiqueZ);
        });

    }

    private void pantallaRuta(String ruta, Button boton) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
        loader.setControllerFactory(SpringContext.getBeanFactory());
            stackCaja.getChildren().clear();
        try{
            Parent root = loader.load();
            Stage stage = (Stage) boton.getScene().getWindow();
            stackCaja.getChildren().add(root);
            stage.show();
        }catch (Exception e){
            e.printStackTrace(System.err);

        }
    }

}
