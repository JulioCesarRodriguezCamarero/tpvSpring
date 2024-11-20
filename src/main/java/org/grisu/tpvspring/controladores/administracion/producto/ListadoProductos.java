package org.grisu.tpvspring.controladores.administracion.producto;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.grisu.tpvspring.SpringContext;
import org.grisu.tpvspring.modelo.Producto;
import org.grisu.tpvspring.servicio.IProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ListadoProductos implements Initializable {
    @FXML
    private AnchorPane anchorListarProducto;

    @FXML
    private TableColumn<Producto, Long> columId;

    @FXML
    private TableColumn<Producto, Double> columPrecio;
    @FXML
    private TableColumn<Producto, ImageView> columImagen;

    @FXML
    private TableColumn<Producto, String> columProducto;

    @FXML
    private TableView<Producto> tablaListarProducto;
    @Autowired
    private IProductoServicio servicio;
    private final ObservableList<Producto> observableList = FXCollections.observableArrayList();
    public static Long miId;
    private Producto producto;
    private Image imagen;
    private StackPane miStackPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableList.clear();
        observableList.addAll(servicio.listar());
        if (observableList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Introduce artículos ");
            alert.setContentText("No hay artículos en la tabla de productos");
            alert.showAndWait();
            return;
        }
        tablaListarProducto.setItems(observableList);
        tablaListarProducto.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        columId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columProducto.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        // Convertir imagen
        columImagen.setCellValueFactory(cellData -> {
            byte[] imagenByte = cellData.getValue().getImagen();
            ByteArrayInputStream bis = new ByteArrayInputStream(imagenByte);
            Image imagen = new Image(bis);
            ImageView imageView = new ImageView(imagen);
            imageView.setFitHeight(35); // Ajusta el tamaño de la imagen
            imageView.setFitWidth(35);
            return new javafx.beans.property.SimpleObjectProperty<>(imageView);
        });

        columId.setStyle("-fx-alignment: center");
        columProducto.setStyle("-fx-alignment: center");
        columPrecio.setStyle("-fx-alignment: center");
        columImagen.setStyle("-fx-alignment: center");
    //todo                                        BUSCAR PRODUCTO POR ID
        tablaListarProducto.setOnMouseClicked(event -> {

            miId =tablaListarProducto.getSelectionModel().getSelectedItem().getId();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/templates/administracion/productos/productoSeleccionado.fxml"));
            loader.setControllerFactory(SpringContext.getBeanFactory());
            miStackPane.getChildren().clear();

            try {
                Parent root = loader.load();
                Stage stage = (Stage) miStackPane.getScene().getWindow();
                miStackPane.getChildren().add(root);
                stage.show();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }


    public void miControlador(StackPane stackProducto) {
        miStackPane = stackProducto;
    }
}
