package com.helisha.inversiones7h.controllers;

import com.helisha.inversiones7h.BootInitializable;
import com.helisha.inversiones7h.entities.Proveedor;
import com.helisha.inversiones7h.services.ProveedorService;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

@Component
@FxmlView("VerProveedores.fxml")
public class VerProveedoresController implements BootInitializable {

  @Autowired
  private FxWeaver fxWeaver;

  @Autowired
  private ProveedorService proveedorService;

  @FXML
  private MFXTextField cedula;

  @FXML
  private MFXTextField nombre;

  @FXML
  private MFXTextField apellido;

  @FXML
  private MFXComboBox<Proveedor.Categoria> categoria;

  @FXML
  private MFXTextField ubicacion;

  @FXML
  private MFXButton editarBtn;

  @FXML
  private MFXPaginatedTableView<Proveedor> tablaProveedores;

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
    categoria.getItems().setAll(FXCollections.observableArrayList(Proveedor.Categoria.values()));

    MFXTableColumn<Proveedor> columnaCedula = new MFXTableColumn<>("Cédula", false, Comparator.comparing(Proveedor::getCedulaIndentidad));
    MFXTableColumn<Proveedor> columnaNombre = new MFXTableColumn<>("Nombre", false, Comparator.comparing(Proveedor::getNombre));
    MFXTableColumn<Proveedor> columnaApellido = new MFXTableColumn<>("Apellido", false, Comparator.comparing(Proveedor::getApellido));
    MFXTableColumn<Proveedor> columnaCat = new MFXTableColumn<>("Categoría", false, Comparator.comparing(Proveedor::getCategoriaString));
    MFXTableColumn<Proveedor> columnaUbicacion = new MFXTableColumn<>("Ubicación", false, Comparator.comparing(Proveedor::getUbicacion));

    columnaCedula.setRowCellFactory(proveedor -> new MFXTableRowCell<>(Proveedor::getCedulaIndentidad));
    columnaNombre.setRowCellFactory(proveedor -> new MFXTableRowCell<>(Proveedor::getNombre));
    columnaApellido.setRowCellFactory(proveedor -> new MFXTableRowCell<>(Proveedor::getApellido));
    columnaCat.setRowCellFactory(proveedor -> new MFXTableRowCell<>(Proveedor::getCategoriaString));
    columnaUbicacion.setRowCellFactory(proveedor -> new MFXTableRowCell<>(Proveedor::getUbicacion));

    tablaProveedores.getTableColumns().setAll(columnaCedula, columnaNombre, columnaApellido, columnaCat, columnaUbicacion);
    tablaProveedores.setRowsPerPage(15);
    tablaProveedores.getFilters().setAll(
      new StringFilter<>("Cédula",Proveedor::getCedulaIndentidad),
      new StringFilter<>("Nombre", Proveedor::getNombre),
      new StringFilter<>("Apellido", Proveedor::getApellido),
      new StringFilter<>("Categoría", Proveedor::getCategoriaString),
      new StringFilter<>("Ubicación", Proveedor::getUbicacion)
    );
    recargarTabla();
  }

  public void recargarTabla(){
    tablaProveedores.setItems(FXCollections.observableArrayList(proveedorService.findAll()));

    tablaProveedores.getSelectionModel().selectionProperty().addListener((MapChangeListener<? super Integer, ? super Proveedor>) change -> {
      Proveedor proveedorSeleccionado = tablaProveedores.getSelectionModel().getLastSelectedValue();

      cedula.setText(proveedorSeleccionado.getCedulaIndentidad());
      nombre.setText(proveedorSeleccionado.getNombre());
      apellido.setText(proveedorSeleccionado.getApellido());
      ubicacion.setText(proveedorSeleccionado.getUbicacion());
      categoria.setValue(proveedorSeleccionado.getCategoria());
    });

    ObservableList<MFXTableColumn<Proveedor>> columnas = tablaProveedores.getTableColumns();
    double anchoTabla = tablaProveedores.getPrefWidth();

    for (var columna : columnas){
      columna.setMinWidth(anchoTabla / 5);
    }
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

  }

  @FXML
  public void editarProducto(ActionEvent event) {
    String cedulaInput = cedula.getText();
    String nombreInput = nombre.getText();
    String apellidoInput = apellido.getText();
    Proveedor.Categoria categoriaInput = categoria.getValue();
    String ubicacionInput = ubicacion.getText();

    // Realiza las validaciones de los campos de que no existan

    if (cedulaInput.isEmpty() || nombreInput.isEmpty() || apellidoInput.isEmpty() || ubicacionInput.isEmpty()) {

      mostrarAlertaError("Error de registro", "Todos los campos son obligatorios", "Por favor, complete todos los campos antes de registrar el proveedor.");
      return;
    }

    //verificamos si ya se registro ese proveedor

    Proveedor proveedor = proveedorService.findByCedulaIdentidad(cedulaInput);

    proveedor.setNombre(nombreInput);
    proveedor.setApellido(apellidoInput);
    proveedor.setCategoria(categoriaInput);
    proveedor.setUbicacion(ubicacionInput);

    proveedorService.save(proveedor);

    //limpiamos campos

    cedula.setText("");
    nombre.setText("");
    apellido.setText("");
    ubicacion.setText("");
    categoria.setText("");

    // Muestra un mensaje de éxito
    recargarTabla();
    mostrarAlertaExito("Éxito", "Proveedor editado exitosamente");
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
