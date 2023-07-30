package com.helisha.inversiones7h.controllers;

import com.helisha.inversiones7h.BootInitializable;
import com.helisha.inversiones7h.entities.Producto;
import com.helisha.inversiones7h.entities.Proveedor;
import com.helisha.inversiones7h.services.ProductoService;
import com.helisha.inversiones7h.services.ProveedorService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
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
import javafx.scene.control.Alert.AlertType;
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
@FxmlView("productos.fxml")
public class ProductosController implements BootInitializable {

  @Autowired
  private FxWeaver fxWeaver;

  @Autowired
  private ProductoService productoService;

  @Autowired
  private ProveedorService proveedorService;

  @FXML
  private MFXSpinner<Integer> cantidadSpinner;

  @FXML
  private MFXTextField nombreField;

  @FXML
  private MFXTextField codigoField;

  @FXML
  private MFXTextField precio;


  int currentValue;

  @FXML
  private MFXComboBox<Proveedor> proveedorField;

  @FXML
  private MFXButton registrarBtn;

  @FXML
  private MFXButton volverAtrasBtn;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    SpinnerModel<Integer> spinnerModel = new IntegerSpinnerModel();
    spinnerModel.setValue(0);
    cantidadSpinner.setSpinnerModel(spinnerModel);

    // añadimos los valores al combobox
    proveedorField.getItems().setAll(FXCollections.observableArrayList(proveedorService.findAll()));

    // añadimos un formateador
    StringConverter<Proveedor> formateador = FunctionalStringConverter.to(proveedor -> (proveedor == null) ? ""
      : "Cédula: " + proveedor.getCedulaIndentidad() + " - " + "Nombre: " + proveedor.getNombre() + " "
      + proveedor.getApellido());

    // añadimos el formateador para cambiar el texto visualizado
    proveedorField.setConverter(formateador);
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

  }

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

  @FXML
  public void registrarProducto(ActionEvent event) {

    String nombreInput = nombreField.getText();
    String codigoInput = codigoField.getText();
    BigDecimal precioInput = BigDecimal.valueOf(Double.parseDouble(precio.getText()));
    Integer cantidadInput = cantidadSpinner.getValue();
    Proveedor proveedor = proveedorField.getValue();

    // Realiza las validaciones de los campos de que no existan

    if (nombreInput.isEmpty() || codigoInput.isEmpty() || precioInput.equals(BigDecimal.valueOf(0)) || cantidadInput.equals(0)) {

      mostrarAlertaError("Error de registro", "Todos los campos son obligatorios", "Por favor, complete todos los campos antes de registrar el cliente.");
      return;
    }
// Obtenemos el Proveedor desde la base de datos filtrado por nombre

    Proveedor proveedorBuscado = proveedorService.findByNombre(proveedor.getNombre());

    // Verificar si se encontró el proveedor
    if (proveedorBuscado == null) {
      mostrarAlertaError("Error de registro", "Proveedor no encontrado", "El proveedor ingresado no existe en la base de datos.");
      return;
    }

//verificamos si producto ya existe 

    Producto productoExiste = productoService.findByCodigo(codigoInput);

    if (productoExiste != null) {
      mostrarAlertaError("Error de registro", "Producto ya registrado", "El producto ya fue ingresado en la base de datos.");
      return;
    }

// agregamos el producto si todas las validaciones son correctas
    Producto producto = new Producto();

    producto.setCodigo(codigoInput);
    producto.setNombre(nombreInput);
    producto.setPrecio(precioInput);
    producto.setCantidad(cantidadInput.longValue());
    producto.setProveedor(proveedor);

    productoService.save(producto);

    // Limpia los campos de entrada después de registrar el producto

    nombreField.clear();
    codigoField.clear();
    cantidadSpinner.setValue(0);
    precio.clear();
    proveedorField.clear();

    // Muestra un mensaje de éxito
    mostrarAlertaExito("Éxito", "Producto registrado exitosamente");

  }

  private void mostrarAlertaError(String titulo, String header, String mensaje) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(titulo);
    alert.setHeaderText(header);
    alert.setContentText(mensaje);
    alert.showAndWait();
  }

  private void mostrarAlertaExito(String titulo, String header) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(titulo);
    alert.setHeaderText(header);
    alert.showAndWait();
  }
}
