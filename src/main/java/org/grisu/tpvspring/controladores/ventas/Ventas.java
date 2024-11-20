package org.grisu.tpvspring.controladores.ventas;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.grisu.tpvspring.modelo.*;
import org.grisu.tpvspring.servicio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@Component
public class Ventas implements Initializable {

    @FXML
    private Button btnCajon;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnEntrada;

    @FXML
    private Button btnFinalizar;

    @FXML
    private Button btnNuevaCuenta;

    @FXML
    private Button btnPreTique;

    @FXML
    private Button btnRapido;

    @FXML
    private Button btnSalida;

    @FXML
    private Button btnSubTotal;

    @FXML
    private Button btnTipoTarifa;

    @FXML
    private Button btnUltimasVentas;

    @FXML
    private Button btnVendedor;

    @FXML
    private TableColumn<Producto, String> columProducto;

    @FXML
    private TableColumn<Producto, Double> columSutotal;

    @FXML
    private TableColumn<Producto, Integer> columUnidad;

    @FXML
    private FlowPane flowProductos;

    @FXML
    private GridPane gridPanel;

    @FXML
    private Label labelFecha;

    @FXML
    private Label labelHora;

    @FXML
    private Label labelTotal;

    @FXML
    private TableView<Producto> tabla;


    @Autowired
    private IProductoServicio servicio;
    @Autowired
    private ILineaVentasServicio servicioLineaVentas;
    @Autowired
    private ITiqueServicio servicioTiques;
    @Autowired
    private ICuentaServicio servicioCuentas;
    @Autowired
    private ICajaPrincipalServicio servicioCajas;
    @Autowired
    private IUsuarioServicio servicioUsuarios;
    private final ObservableList<Producto> productosObservable = FXCollections.observableArrayList();
    private final List<Producto> productosList = FXCollections.observableArrayList();
    private int numeroGrid = 1;

    //-------------------------------------------------------------
    private CajaPrincipal cajaPrincipal;
    private Cuenta miCuenta;
    private Usuario usuario;
    private Tique miTique;
    private List<LineaVentas> lineaVentasList;
    private double sumaTiques;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.productosList.addAll(servicio.listar());
        this.cajaPrincipal = servicioCajas.listar().getLast();
        miCuenta = servicioCuentas.buscarActivo(true);


        // todo iniciar aplicación
        inicioAplicacion();

        // todo             fecha y hora
        fachaYHora();

        // todo                 eliminar
        btnEliminar.setOnAction(this::restarProducto);

        // todo grid panel comprobar si son números o letras
        gridPanel.setOnMouseClicked(this::comprobarNumeros);

        // todo         venta Rápida
        btnRapido.setOnAction(this::finalizaRapido);

        // todo                 subtotal
        btnSubTotal.setOnAction(this::subTotal);

        //todo          últimas ventas
        btnUltimasVentas.setOnAction(event -> {

        });
        // todo             Usuario Registrado
        btnVendedor.setOnAction(event -> {

        });
    }

    private void inicioAplicacion() {
        if (miTique != null) {
            miTique = servicioTiques.listar().getLast();

        } else {
            miTique = new Tique();

        }
        if (!miTique.isActivo()) {
            miTique = new Tique();
        }


        miTique.setActivo(true);
        miCuenta.setTiques(Collections.singletonList(miTique));
        servicioCuentas.agregar(miCuenta);
        flowProductos.getChildren().clear();
        flowProductos.setHgap(20);
        flowProductos.setVgap(20);

        for (Producto producto : productosList) {
            try {
                ByteArrayInputStream bis = new ByteArrayInputStream(producto.getImagen());
                Image img = new Image(bis);
                ImageView imageView = new ImageView(img);
                imageView.setFitWidth(75);
                imageView.setFitHeight(75);
                imageView.setPreserveRatio(true);
                VBox vbox = new VBox();
                vbox.setAlignment(Pos.CENTER);
                vbox.setSpacing(5);

                Label nombreLabel = new Label(producto.getNombre());
                Label precioLabel = new Label(String.format("€%.2f", producto.getPrecio()));
                vbox.getChildren().addAll(imageView, nombreLabel, precioLabel);
                flowProductos.getChildren().add(vbox);

                // todo            agregar productos a la tabla de ventas
                imageView.setOnMouseClicked(event -> sumaProductos(producto));

            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        }
    }

    protected void finalizaRapido(ActionEvent event) {
        this.lineaVentasList = new ArrayList<>();
        miTique = servicioTiques.listar().stream().filter(Tique::isActivo).findFirst().orElse(null);
        miTique = servicioTiques.buscarActivo(true);
        miCuenta = servicioCuentas.buscarActivo(true);

        if (!productosObservable.isEmpty()) {
            lineaVentasList = productosObservable.stream().map(elem -> {
                LineaVentas productoVendido = new LineaVentas();
                productoVendido.setFecha(new Date());
                productoVendido.setProducto(elem.getNombre());
                productoVendido.setUnidades(elem.getCantidad());
                productoVendido.setSubTotal(elem.getSubTotal());
                return productoVendido;
            }).collect(Collectors.toList());

            double suma = this.productosObservable.stream().mapToDouble(Producto::getSubTotal).sum();

            if (cajaPrincipal.isEstaAbierta()) {
                miTique.setFecha(new Date());
                miTique.setTotal(suma);

                limpiar();
            }

        }
    }


    private void subTotal(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("SUBTOTAL");
        alert.setContentText("Subtotal : " + labelTotal.getText());
        // Cambiar el tamaño y la fuente del texto
        alert.getDialogPane().setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 24px;");
        alert.showAndWait();
    }

    private void sumaProductos(Producto producto) {
        producto.setCantidad(producto.getCantidad() + numeroGrid);
        producto.setSubTotal(producto.getPrecio() * producto.getCantidad());

        // Verifica si el producto ya está en la lista observable
        if (!productosObservable.contains(producto)) {
            productosObservable.add(producto);
        } else {
            // Actualiza el producto existente en la lista observable
            int index = productosObservable.indexOf(producto);
            productosObservable.set(index, producto);
        }
        actualizaTabla();
    }

    private void restarProducto(ActionEvent event) {
        if (!productosObservable.isEmpty()) {
            Producto ultimoProducto = productosObservable.getLast();

            // Verifica si el producto ya está en la lista observable y tiene más de una unidad
            if (productosObservable.contains(ultimoProducto) && ultimoProducto.getCantidad() > 1) {
                ultimoProducto.setCantidad(ultimoProducto.getCantidad() - 1);
                ultimoProducto.setSubTotal(ultimoProducto.getPrecio() * ultimoProducto.getCantidad());
                // Actualiza el producto existente en la lista observable
                int index = productosObservable.indexOf(ultimoProducto);
                productosObservable.set(index, ultimoProducto);
            } else if (ultimoProducto.getCantidad() == 1) {
                productosObservable.remove(ultimoProducto);
            }
            actualizaTabla();
        }
    }


    private void comprobarNumeros(MouseEvent event) {
        Node targetNode = (Node) event.getTarget();
        while (targetNode != null && !(targetNode instanceof Label)) {
            targetNode = targetNode.getParent();
        }
        if (targetNode instanceof Label clickedLabel) {
            String labelValue = clickedLabel.getText();
            if (!(labelValue.equals("BORRAR") || labelValue.equals("X") || labelValue.equals("0,00") || labelValue.equals(",") || labelValue.equals("Mnto"))) {
                numeroGrid = Integer.parseInt(labelValue);
            }
        }
    }

    private void fachaYHora() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        labelFecha.setText(LocalDate.now().format(dtf));
        labelHora.setText(LocalTime.now().getHour() + ":" + LocalTime.now().getMinute());
    }

    private void limpiar() {
        tabla.getItems().clear();
        productosList.forEach(producto -> {
            producto.setCantidad(0);
            producto.setSubTotal(0);
        });
        miTique.setActivo(false);
        servicioTiques.agregar(miTique);
        labelTotal.setText("0,00");
        inicioAplicacion();
    }

    private void actualizaTabla() {
        numeroGrid = 1;
        tabla.setItems(productosObservable);
        columUnidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        columProducto.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columSutotal.setCellValueFactory(new PropertyValueFactory<>("subTotal"));
        double num = this.productosObservable.stream().mapToDouble(Producto::getSubTotal).sum();
        labelTotal.setText(String.format("%.2f", num));
    }

    public void nombreBoton(MouseEvent event) {

        if (event.getSource() instanceof Button boton) {
            this.miCuenta = servicioCuentas.buscarPorNombre(boton.getText());


        }


    }
}
