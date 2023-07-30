package com.helisha.inversiones7h.controllers;

import com.helisha.inversiones7h.BootInitializable;
import com.helisha.inversiones7h.entities.Producto;
import com.helisha.inversiones7h.entities.Proveedor;
import com.helisha.inversiones7h.services.ProductoService;
import com.helisha.inversiones7h.services.ProveedorService;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.controls.models.spinner.IntegerSpinnerModel;
import io.github.palexdev.materialfx.controls.models.spinner.SpinnerModel;
import io.github.palexdev.materialfx.filter.BigDecimalFilter;
import io.github.palexdev.materialfx.filter.LongFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import io.github.palexdev.materialfx.utils.others.FunctionalStringConverter;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
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
import java.util.Comparator;
import java.util.ResourceBundle;

@Component
@FxmlView("VerProductos.fxml")
public class VerProductoController implements BootInitializable {

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

  @FXML
  private MFXComboBox<Proveedor> proveedorField;

  @FXML
  private MFXButton editarBtn;

  @FXML
  private MFXPaginatedTableView<Producto> tablaProductos;

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
    MFXTableColumn<Producto> columnaProductos = new MFXTableColumn<>("Código", false, Comparator.comparing(Producto::getCodigo));
    MFXTableColumn<Producto> columnaNombre = new MFXTableColumn<>("Nombre", false, Comparator.comparing(Producto::getNombre));
    MFXTableColumn<Producto> columnaProveedor = new MFXTableColumn<>("Proveedor", false, Comparator.comparing(Producto::getProveedor));
    MFXTableColumn<Producto> columnaCantidad = new MFXTableColumn<>("Cantidad disponible", false, Comparator.comparing(Producto::getCantidad));
    MFXTableColumn<Producto> columnaPrecio = new MFXTableColumn<>("Precio", false, Comparator.comparing(Producto::getPrecioString));

    columnaProductos.setRowCellFactory(producto -> new MFXTableRowCell<>(Producto::getCodigo));
    columnaNombre.setRowCellFactory(producto -> new MFXTableRowCell<>(Producto::getNombre));
    columnaProveedor.setRowCellFactory(producto -> new MFXTableRowCell<>(Producto::getProveedor));
    columnaPrecio.setRowCellFactory(producto -> new MFXTableRowCell<>(Producto::getPrecioString));
    columnaCantidad.setRowCellFactory(producto -> new MFXTableRowCell<>(Producto::getCantidad));

    tablaProductos.getTableColumns().setAll(columnaProductos,columnaNombre,columnaProveedor,columnaCantidad, columnaPrecio);
    tablaProductos.setRowsPerPage(15);
    tablaProductos.getFilters().setAll(
      new StringFilter<>("Código",Producto::getCodigo),
      new StringFilter<>("Nombre", Producto::getNombre),
      new BigDecimalFilter<>("Precio", Producto::getPrecio),
      new LongFilter<>("Cantidad", Producto::getCantidad),
      new StringFilter<>("Proveedor", Producto::getProveedor)
    );
    recargarTabla();

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

  public void recargarTabla(){
    tablaProductos.setItems(FXCollections.observableArrayList(productoService.findAll()));

    tablaProductos.getSelectionModel().selectionProperty().addListener((MapChangeListener<? super Integer, ? super Producto>) change -> {
      Producto productoSeleccionado = tablaProductos.getSelectionModel().getLastSelectedValue();

      cantidadSpinner.setValue(productoSeleccionado.getCantidad().intValue());
      nombreField.setText(productoSeleccionado.getNombre());
      codigoField.setText(productoSeleccionado.getCodigo());
      precio.setText(productoSeleccionado.getPrecio().toString());
      proveedorField.setValue(productoSeleccionado.proveedor);
    });

    ObservableList<MFXTableColumn<Producto>> columnas = tablaProductos.getTableColumns();
    double anchoTabla = tablaProductos.getPrefWidth();

    for (var columna : columnas){
      columna.setMinWidth(anchoTabla / 5);
    }
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

  }

  @FXML
  public void editarProducto(ActionEvent event) {
    String nombreInput = nombreField.getText();
    String codigoInput = codigoField.getText();
    BigDecimal precioInput = BigDecimal.valueOf(Double.parseDouble(precio.getText()));
    Integer cantidadInput = cantidadSpinner.getValue();
    Proveedor proveedor = proveedorField.getValue();

    // Realiza las validaciones de los campos de que no existan

    if (nombreInput.isEmpty() || codigoInput.isEmpty() || precioInput == BigDecimal.valueOf(0) || cantidadInput.equals(0)) {

      mostrarAlertaError("Error al editar", "Todos los campos son obligatorios", "Por favor, complete todos los campos antes de registrar el cliente.");
      return;
    }
// Obtenemos el Proveedor desde la base de datos filtrado por nombre

    Proveedor proveedorBuscado = proveedorService.findByNombre(proveedor.getNombre());

    // Verificar si se encontró el proveedor
    if (proveedorBuscado == null) {
      mostrarAlertaError("Error de registro", "Proveedor no encontrado", "El proveedor ingresado no existe en la base de datos.");
      return;
    }

    Producto producto = productoService.findByCodigo(codigoInput);

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
    mostrarAlertaExito("Éxito", "Producto editado exitosamente");
    recargarTabla();
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
}
