package com.helisha.inversiones7h.controllers;

import com.helisha.inversiones7h.BootInitializable;
import com.helisha.inversiones7h.entities.Compra;
import com.helisha.inversiones7h.entities.Producto;
import com.helisha.inversiones7h.entities.Proveedor;
import com.helisha.inversiones7h.services.CompraService;
import com.helisha.inversiones7h.services.ProductoService;
import com.helisha.inversiones7h.services.ProveedorService;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXSpinner;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.models.spinner.IntegerSpinnerModel;
import io.github.palexdev.materialfx.controls.models.spinner.SpinnerModel;
import io.github.palexdev.materialfx.utils.others.FunctionalStringConverter;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;



@Component
@FxmlView("compra.fxml")
public class CompraController implements BootInitializable {
  
  @Autowired
  private FxWeaver fxWeaver;

  @Autowired
  private CompraService CompraService;

  @Autowired
  private ProductoService productoService;

  @Autowired
  private ProveedorService proveedorService;

  @FXML
  private MFXFilterComboBox<Producto> producto;

  @FXML
  private MFXFilterComboBox<Proveedor> proveedor;

  @FXML
  private MFXTextField id;

  @FXML
  private MFXTextField monto;

  @FXML
  private MFXButton registrarBtn;

  @FXML
  private MFXButton volverAtrasBtn;

  @FXML
  private MFXSpinner<Integer> cantidadSpinner;

  @Override
  public void initConstruct() {
     

  }

  @Override
  public void stage(Stage primaryStage) {

  }

  @Override
  public Node initView() {
    return null;
  }

  @Override
  public void initValidator() {

  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    SpinnerModel<Integer> spinnerModel = new IntegerSpinnerModel();
    spinnerModel.setValue(0);
    cantidadSpinner.setSpinnerModel(spinnerModel);

    cantidadSpinner.valueProperty().addListener(event -> {
      System.out.println("holaaa");
      Producto productoInput = producto.getValue();
      Integer cantidadInput = cantidadSpinner.getValue();
      monto.setText((productoInput.getPrecio().multiply(new BigDecimal(cantidadInput))).toString());
    });

    // añadimos los valores al combobox
    proveedor.getItems().setAll(FXCollections.observableArrayList(proveedorService.findAll()));
    producto.getItems().setAll(FXCollections.observableArrayList(productoService.findAll()));

    // añadimos un formateador
    StringConverter<Proveedor> formateadorProveedores = FunctionalStringConverter.to(proveedor -> (proveedor == null) ? ""
      : "Cédula: " + proveedor.getCedulaIndentidad() + " - " + "Nombre: " + proveedor.getNombre() + " "
      + proveedor.getApellido());

    StringConverter<Producto> formateadorProductos = FunctionalStringConverter.to(producto -> (producto == null) ? ""
      : "#" + producto.getCodigo() + " - " + producto.getNombre());

    // añadimos el formateador para cambiar el texto visualizado
    proveedor.setConverter(formateadorProveedores);
    producto.setConverter(formateadorProductos);
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

  }

  
  // Método para registrar la compra al hacer clic en el botón "Registrar"
  
  @FXML 
  public void registrarCompra(ActionEvent event) {

    // Obtener los datos ingresados por el usuario

    Producto productoInput = producto.getValue();
    Proveedor proveedorInput = proveedor.getValue();
    Integer cantidad = cantidadSpinner.getValue();
    BigDecimal montoTotal = new BigDecimal(monto.getText());

    // Crear una nueva instancia de Compra con los datos proporcionados
    Compra compra = new Compra();

    productoInput.setCantidad(productoInput.getCantidad() + cantidad);
    
    compra.setProducto(productoInput);
    compra.setProveedor(proveedorInput);
    compra.setCantidad(cantidad.longValue());
    compra.setMontoTotal(montoTotal);

    // Guardar la compra utilizando el servicio CompraService
    CompraService.save(compra);

    producto.clear();
    proveedor.clear();
    cantidadSpinner.setValue(0);
    monto.setText("");

    mostrarAlertaExito("¡Exitoso!", "Compra registrada exitosamente");
  }

  private void mostrarAlertaError(String titulo, String header, String mensaje) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(titulo);
    alert.setHeaderText(header);
    alert.setContentText(mensaje);
    alert.showAndWait();
  }

  private void mostrarAlertaExito(String titulo, String header) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(titulo);
    alert.setHeaderText(header);
    alert.showAndWait();
  }
  public void refrescarCampos() {
    this.proveedor.getItems().removeAll(this.proveedor.getItems());
    this.proveedor.getItems().setAll(proveedorService.findAll());
    this.producto.getItems().removeAll(this.producto.getItems());
    this.producto.getItems().setAll(productoService.findAll());

  }

}
