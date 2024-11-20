package org.grisu.tpvspring.controladores.administracion.producto;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.grisu.tpvspring.modelo.Producto;
import org.grisu.tpvspring.servicio.IProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static org.grisu.tpvspring.controladores.administracion.producto.ListadoProductos.miId;

@Component
public class ProductoSeleccionado implements Initializable {

    @Autowired
    private IProductoServicio servicio;

    @FXML
    private Button btnActualizarProducto;

    @FXML
    private Button btnCambiarImagen;

    @FXML
    private Button btnCancelar;

    @FXML
    private ImageView imageViewImagen;

    @FXML
    private TextField textDescripcion;

    @FXML
    private TextField textPrecio;

    private Producto producto;
    private Image imagen;
    private File file;
    private String path;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            producto= servicio.buscar(miId);
            textDescripcion.setText(producto.getNombre());
            textPrecio.setText(String.valueOf(producto.getPrecio()));
        //       todo           convertir imagen
        byte[] imagenByte = producto.getImagen();
        ByteArrayInputStream bis = new ByteArrayInputStream(imagenByte);
        imagen = new Image(bis);
        imageViewImagen.setImage(imagen);


            // todo             Actualizar Imagen
        btnCambiarImagen.setOnAction(event->{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccione un vehiculo");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Archivos", "*.jpg", "*.png", "*.bmp", "*.gif", "*.svg", "*.jpeg"));
            Stage stage = (Stage) imageViewImagen.getScene().getWindow();
            file = fileChooser.showOpenDialog(stage);

            if (file == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("""
                               ¡Error!
                        Seleccione una imagen, por favor""");
                alert.showAndWait();
                return;
            } else {
                path = file.toURI().toString();
                this.imagen = new Image(path);
            }
            imageViewImagen.setImage(imagen);
        });
        // todo                Actualizar producto
        btnActualizarProducto.setOnAction(event->{
            producto.setNombre(textDescripcion.getText());
            producto.setPrecio(Double.parseDouble(textPrecio.getText()));

        // todo                Actualizar Imagen
            if (path == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("""
                               ¡Error!
                        Seleccione una imagen, por favor""");
                alert.showAndWait();
            } else {
                try (FileInputStream fis = new FileInputStream(file)) {
                    // Obtiene la longitud del archivo y crea un arreglo de bytes
                    byte[] imagenBytes = new byte[(int) file.length()];

                    // Lee los bytes del archivo directamente en el arreglo imagenBytes
                    int bytesRead = fis.read(imagenBytes);

                    if (bytesRead != -1) { // Verifica si se leyeron bytes
                        producto.setImagen(imagenBytes); // Guardar la imagen en el objeto vehículo
                        servicio.agregar(producto); // Guarda el vehículo con la imagen

                    } else {
                        System.out.println("No se pudieron leer bytes del archivo.");
                    }
                } catch (IOException ex) {
                    System.out.println("Error: " + ex.getMessage());
                    ex.printStackTrace(System.out);
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Actualización");
                alert.setContentText("Producto actualizado con éxito");
                alert.showAndWait();

            }
        });
        btnCancelar.setOnAction(event->{
           Stage stage = (Stage) btnCancelar.getScene().getWindow();
           stage.close();
        });
    }
}
