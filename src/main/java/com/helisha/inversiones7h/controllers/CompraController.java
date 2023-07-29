package com.helisha.inversiones7h.controllers;

import com.helisha.inversiones7h.BootInitializable;
import com.helisha.inversiones7h.entities.Compra;
import com.helisha.inversiones7h.entities.Producto;
import com.helisha.inversiones7h.entities.Proveedor;
import com.helisha.inversiones7h.services.CompraService;
import com.helisha.inversiones7h.services.ProductoService;
import com.helisha.inversiones7h.services.ProveedorService;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXSpinner;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.models.spinner.IntegerSpinnerModel;
import io.github.palexdev.materialfx.controls.models.spinner.SpinnerModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
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
  private ProductoService ProductoService;

  @Autowired
  private ProveedorService ProveedorService;

  @FXML
  private MFXTextField Producto;

  @FXML
  private MFXTextField Proveedor;

  @FXML
  private MFXTextField id;

  @FXML
  private MFXTextField monto;

  @FXML
  private MFXButton registrarBtn;

  @FXML
  private MFXButton volverAtrasBtn;

  @FXML
  private MFXSpinner<Integer> CantidadSpinner;

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
    CantidadSpinner.setSpinnerModel(spinnerModel);
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

  }

  
  // Método para registrar la compra al hacer clic en el botón "Registrar"
  
  @FXML 
  public void registrarCompra(ActionEvent event) {

    // Obtener los datos ingresados por el usuario

    String productoNombre = Producto.getText();
    String proveedorNombre = Proveedor.getText();
    Integer cantidad = CantidadSpinner.getValue();
    BigDecimal montoTotal = new BigDecimal(monto.getText());

    // Obtener el Producto y el Proveedor desde la base de datos (asumiendo que ya existen)

    Producto producto = ProductoService.findByNombre(productoNombre);
    Proveedor proveedor = ProveedorService.findByNombre(proveedorNombre);

    // Crear una nueva instancia de Compra con los datos proporcionados
    Compra compra = new Compra();
    
    compra.setProducto(producto);
    compra.setProveedor(proveedor);
    compra.setCantidad(cantidad.longValue());
    compra.setMontoTotal(montoTotal);

    // Guardar la compra utilizando el servicio CompraService
    CompraService.save(compra);

    
  }


  
}
