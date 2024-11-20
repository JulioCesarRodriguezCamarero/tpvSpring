package org.grisu.tpvspring.controladores.caja;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.grisu.tpvspring.modelo.CajaPrincipal;
import org.grisu.tpvspring.servicio.ICajaPrincipalServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

@Component
public class Apertura implements Initializable {
    @FXML
    private GridPane gridPanel;

    @FXML
    private Label lblBorrar;

    @FXML
    private TextField txtEfectivo;
    @FXML
    private Button btnAceptar;

    @FXML
    private Button btnCancelar;
    private double efectivoCaja;
    private CajaPrincipal principal;
    @Autowired
    private ICajaPrincipalServicio servicio;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StringBuilder stb = new StringBuilder();
        try {
            principal = servicio.listar().getLast();

        }catch (Exception e){
            System.out.println(e.getMessage()+ "*****************");
            System.out.println("Error , No existe el servicio, creamos una caja");
            principal = new CajaPrincipal();
            servicio.agregar(principal);
        }


        gridPanel.setOnMouseClicked(event -> {
            if (principal.isEstaAbierta()) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setHeaderText("Caja abierta");
                alerta.setContentText("Haga un Tique Z para iniciar otra Apertura");
                alerta.showAndWait();
                gridPanel.getScene().getWindow().hide();
            }
            Node targetNode = (Node) event.getTarget();
            while (targetNode != null && !(targetNode instanceof Label)) {
                targetNode = targetNode.getParent();
            }
            if (targetNode instanceof Label clickedLabel) {
                String labelValue = clickedLabel.getText();
                if (!(labelValue.equals("BORRAR"))) {
                    stb.append(clickedLabel.getText());
                    txtEfectivo.setText(stb.toString());
                } else {
                    txtEfectivo.setText("");
                    stb.delete(0, stb.length());

                }
            }
        });
        btnAceptar.setOnAction(event -> {

            double efectivo = Double.parseDouble(txtEfectivo.getText().replace(",", "."));

            this.efectivoCaja += efectivo;
            principal.setAperturaDia(new Date());
            principal.setSaldoApertura(efectivoCaja);
            principal.setEstaAbierta(true);
            servicio.agregar(principal);

            btnAceptar.getScene().getWindow().hide();

        });
        btnCancelar.setOnAction(event -> {
            btnCancelar.getScene().getWindow().hide();
        });
    }
}
