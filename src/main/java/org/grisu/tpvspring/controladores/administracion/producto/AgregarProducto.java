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
import org.grisu.tpvspring.servicio.ProductoServicioImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class AgregarProducto implements Initializable {

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnAgregarImagen;

    @FXML
    private Button btnCancelar;

    @FXML
    private ImageView imageViewImagen;

    @FXML
    private TextField textDescripcion;

    @FXML
    private TextField textPrecio;

    @Autowired
    private ProductoServicioImpl servicio;
    private Producto producto;
    private File file = new File("C:\\Curso-java\\grisu\\tpvSpring\\src\\main\\resources\\imagenes\\iconos\\question.jpg");
    private String path;
    private Image imagen;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //todo************************  Agregar imagen  *************************
        btnAgregarImagen.setOnAction(e -> {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccione un producto");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Archivos", "*.jpg", "*.png", "*.bmp", "*.gif", "*.svg", "*.jpeg"));
            File defaultDirectory = new File("C:\\Curso-java\\grisu\\tpvSpring\\src\\main\\resources\\imagenes\\productos");
            fileChooser.setInitialDirectory(defaultDirectory);
            Stage stage = (Stage) imageViewImagen.getScene().getWindow();
            file = fileChooser.showOpenDialog(stage);
            System.out.println(file);
            if (file == null) return;
            path = file.toURI().toString();
            System.out.println(path);
            imagen = new Image(path);
            imageViewImagen.setImage((Image) imagen);
        });
        //todo************************  Agregar producto  *************************
        btnAgregar.setOnAction(e -> {
            producto = new Producto();
            producto.setNombre(textDescripcion.getText());
            producto.setPrecio(Double.parseDouble(textPrecio.getText()));
            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] imagenBytes = new byte[(int) file.length()];
                fis.read(imagenBytes);
                producto.setImagen(imagenBytes); // Guardar la imagen en el objeto vehículo
                servicio.agregar(producto);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Agregar Producto");
                alert.setHeaderText("Información");
                alert.setContentText("Producto creado con éxito");
                alert.show();
                limpiar();

            } catch (IOException exception) {
                System.out.println("Error al leer el archivo de la imagen");
                exception.printStackTrace(System.out);
            }
        });
        btnCancelar.setOnAction(e -> {
            Stage stage = (Stage) btnCancelar.getScene().getWindow();
            stage.close();
        });
    }

    private void limpiar() {
        textDescripcion.clear();
        textPrecio.clear();
        imageViewImagen.setImage(null);

    }
}
