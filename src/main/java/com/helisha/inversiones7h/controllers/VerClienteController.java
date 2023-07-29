package com.helisha.inversiones7h.controllers;

import com.helisha.inversiones7h.BootInitializable;
import com.helisha.inversiones7h.entities.Cliente;
import com.helisha.inversiones7h.services.ClienteService;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.BigDecimalFilter;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import io.github.palexdev.materialfx.utils.others.FunctionalStringConverter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.util.StringConverter;
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
@FxmlView("VerCliente.fxml")
public class VerClienteController implements BootInitializable {
  @FXML
  private MFXTextField cedulaInput;

  @FXML
  private MFXTextField nombreInput;

  @FXML
  private MFXTextField apellidoInput;


  @FXML
  private MFXTextField telefonoInput;

  @FXML
  private MFXTextField direccionInput;

  @FXML
  private MFXButton editarClienteBtn;

  @FXML
  private MFXPaginatedTableView<Cliente> tablaClientes;

  @Autowired
  private FxWeaver fxWeaver;

  @Autowired
  private ClienteService cService;

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
    MFXTableColumn<Cliente> columnaCedula =new MFXTableColumn<>("Cédula de Identidad", false, Comparator.comparing(Cliente::getCedulaIdentidad));
    MFXTableColumn<Cliente> columnaNombre = new MFXTableColumn<>("Nombre", true, Comparator.comparing(Cliente::getNombre));
    MFXTableColumn<Cliente> columnaApellido = new MFXTableColumn<>("Apellido", true, Comparator.comparing(Cliente::getApellido));
    MFXTableColumn<Cliente> columnaTelefono = new MFXTableColumn<>("Número de Teléfono", false, Comparator.comparing(Cliente::getTelefono));
    MFXTableColumn<Cliente> columnaDireccion = new MFXTableColumn<>("Dirección", true, Comparator.comparing(Cliente::getDireccion));
    MFXTableColumn<Cliente> columnaGastoTotal = new MFXTableColumn<>("Gasto total en compras", true, Comparator.comparing(Cliente::getGastoTotal));

    columnaCedula.setRowCellFactory(cliente -> new MFXTableRowCell<>(Cliente::getCedulaIdentidad));
    columnaNombre.setRowCellFactory(cliente -> new MFXTableRowCell<>(Cliente::getNombre));
    columnaApellido.setRowCellFactory(cliente -> new MFXTableRowCell<>(Cliente::getApellido));
    columnaTelefono.setRowCellFactory(cliente -> new MFXTableRowCell<>(Cliente::getTelefono));
    columnaDireccion.setRowCellFactory(cliente -> new MFXTableRowCell<>(Cliente::getDireccion));
    columnaGastoTotal.setRowCellFactory(cliente -> new MFXTableRowCell<>(Cliente::getGastoTotal));

    columnaCedula.setAlignment(Pos.CENTER_RIGHT);

    tablaClientes.getTableColumns().setAll(columnaCedula, columnaNombre, columnaApellido, columnaTelefono, columnaDireccion, columnaGastoTotal);
    tablaClientes.getFilters().setAll(
      new StringFilter<>("ID", Cliente::getCedulaIdentidad),
      new StringFilter<>("Nombre", Cliente::getNombre),
      new StringFilter<>("Apellido", Cliente::getApellido),
      new StringFilter<>("Número de Teléfono", Cliente::getTelefono),
      new StringFilter<>("Dirección", Cliente::getDireccion),
      new BigDecimalFilter<>("Gasto total en compras", Cliente::getGastoTotal)
    );

    recargarTabla();
  }

  public void recargarTabla(){
    tablaClientes.setItems(FXCollections.observableArrayList(cService.findAll()));
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

  }
}
