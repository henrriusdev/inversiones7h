package com.hbourgeot.inversiones7h.controllers;

import com.hbourgeot.inversiones7h.BootInitializable;
import com.hbourgeot.inversiones7h.services.ProductoService;
import com.hbourgeot.inversiones7h.services.ProveedorService;
import com.hbourgeot.inversiones7h.entities.Producto;
import com.hbourgeot.inversiones7h.entities.Proveedor;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXSpinner;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.models.spinner.IntegerSpinnerModel;
import io.github.palexdev.materialfx.controls.models.spinner.SpinnerModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("producto.fxml")
public class ProductosController implements BootInitializable {
 
  @Autowired
  private FxWeaver fxWeaver;

  @Autowired
  private ProductoService ProductoService;

  @Autowired
  private ProveedorService ProveedorService;
  
  @FXML
  private MFXSpinner<Integer> cantidadSpinner;

  @FXML
  private MFXTextField nombreField;

  @FXML
  private MFXTextField codigoField;

  int currentValue;

  @FXML
  private MFXTextField proveedorField;

  @FXML
  private MFXButton registrarBtn;

  @FXML
  private MFXButton volverAtrasBtn;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    SpinnerModel<Integer> spinnerModel = new IntegerSpinnerModel();
    spinnerModel.setValue(0);
    cantidadSpinner.setSpinnerModel(spinnerModel);
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
  public void registrarProducto(ActionEvent event){

    String nombreInput = nombreField.getText();
    String codigoInput = codigoField.getText();
    Integer cantidadInput = cantidadSpinner.getValue();
    String proveedorNombre = proveedorField.getText();

  // Realiza las validaciones de los campos de que no existan

    if(nombreInput.isEmpty()|| codigoInput.isEmpty()|| cantidadInput.equals(0)){

      mostrarAlertaError("Error de registro", "Todos los campos son obligatorios", "Por favor, complete todos los campos antes de registrar el cliente.");
        return;
    }
// Obtenemos el Proveedor desde la base de datos filtrado por nombre

    Proveedor proveedor  = ProveedorService.findByNombre(proveedorNombre);

    // Verificar si se encontró el proveedor
    if (proveedor == null) {
    mostrarAlertaError("Error de registro", "Proveedor no encontrado", "El proveedor ingresado no existe en la base de datos.");
    return;
}

//verificamos si producto ya existe 

    Producto productoExiste = ProductoService.findByCodigo(codigoInput);
    
    if(productoExiste != null){
      mostrarAlertaError("Error de registro", "Producto ya registrado", "El producto ya fue ingresado en la base de datos.");
        return;
    }

// agregamos el producto si todas las validaciones son correctas
    Producto producto = new Producto();

      producto.setCodigo(codigoInput);
      producto.setNombre(nombreInput);
      producto.setCantidad(cantidadInput.longValue());

    ProductoService.save(producto);

    // Limpia los campos de entrada después de registrar el producto

    nombreField.clear();
    codigoField.clear();
    cantidadSpinner.setValue(0);

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
