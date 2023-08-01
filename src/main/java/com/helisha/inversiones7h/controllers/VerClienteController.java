package com.helisha.inversiones7h.controllers;

import com.helisha.inversiones7h.BootInitializable;
import com.helisha.inversiones7h.entities.Cliente;
import com.helisha.inversiones7h.services.ClienteService;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.BigDecimalFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
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
@FxmlView("VerCliente.fxml")
public class VerClienteController implements BootInitializable {
  @FXML
  private MFXTextField cedula;

  @FXML
  private MFXTextField nombreCliente;

  @FXML
  private MFXTextField apellidoCliente;


  @FXML
  private MFXTextField telefonoCliente;

  @FXML
  private MFXTextField direccionCliente;

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
    // añadimos las columnas
    MFXTableColumn<Cliente> columnaCedula =new MFXTableColumn<>("Cédula de Identidad", false, Comparator.comparing(Cliente::getCedulaIdentidad));
    MFXTableColumn<Cliente> columnaNombre = new MFXTableColumn<>("Nombre", false, Comparator.comparing(Cliente::getNombre));
    MFXTableColumn<Cliente> columnaApellido = new MFXTableColumn<>("Apellido", false, Comparator.comparing(Cliente::getApellido));
    MFXTableColumn<Cliente> columnaTelefono = new MFXTableColumn<>("Número de Teléfono", false, Comparator.comparing(Cliente::getTelefono));
    MFXTableColumn<Cliente> columnaDireccion = new MFXTableColumn<>("Dirección", false, Comparator.comparing(Cliente::getDireccion));
    MFXTableColumn<Cliente> columnaGastoTotal = new MFXTableColumn<>("Gasto total en compras", false, Comparator.comparing(Cliente::getGastoTotal));

    // usamos
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

    tablaClientes.getSelectionModel().setAllowsMultipleSelection(false);

    recargarTabla();
  }

  public void recargarTabla(){
    tablaClientes.setItems(FXCollections.observableArrayList(cService.findAll()));

    tablaClientes.getSelectionModel().selectionProperty().addListener((MapChangeListener<? super Integer, ? super Cliente>) change -> {
      Cliente clienteSeleccionado = tablaClientes.getSelectionModel().getLastSelectedValue();

      cedula.setText(clienteSeleccionado.getCedulaIdentidad());
      nombreCliente.setText(clienteSeleccionado.getNombre());
      apellidoCliente.setText(clienteSeleccionado.getApellido());
      telefonoCliente.setText(clienteSeleccionado.getTelefono());
      direccionCliente.setText(clienteSeleccionado.getDireccion());
    });
  }

  @FXML
  public void editar(ActionEvent event) {

    String cedulaInput = cedula.getText();
    String nombreInput = nombreCliente.getText();
    String apellidoInput = apellidoCliente.getText();
    String dirInput = direccionCliente.getText();
    String tlfInput = telefonoCliente.getText();

    // Realiza las validaciones de los campos de que no existan
    if (cedulaInput.isEmpty() || nombreInput.isEmpty() || apellidoInput.isEmpty() || dirInput.isEmpty() || tlfInput.isEmpty()) {
      mostrarAlertaError("Error de registro", "Todos los campos son obligatorios", "Por favor, complete todos los campos antes de registrar el cliente.");
      return;
    }


    // Verificar si el cliente ya existe en la base de datos
    Cliente cliente = cService.findByCedulaIdentidad(cedulaInput);


    //Configuramos  los datos a insertar
    cliente.setCedulaIdentidad(cedulaInput);
    cliente.setNombre(nombreInput);
    cliente.setApellido(apellidoInput);
    cliente.setTelefono(tlfInput);
    cliente.setDireccion(dirInput);

    cService.save(cliente);

    // Limpia los campos de entrada después de registrar el cliente

    cedula.setText("");
    nombreCliente.setText("");
    apellidoCliente.setText("");
    direccionCliente.setText("");
    telefonoCliente.setText("");

    // Muestra un mensaje de éxito
    recargarTabla();
    mostrarAlertaExito("Éxito", "Cliente editado exitosamente");
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

  public void restablecer(){
    nombreCliente.clear();
    apellidoCliente.clear();
    cedula.clear();
    direccionCliente.clear();
    telefonoCliente.clear();
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

  }
}
