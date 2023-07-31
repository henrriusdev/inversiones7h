package com.helisha.inversiones7h.controllers;

import com.helisha.inversiones7h.BootInitializable;
import com.helisha.inversiones7h.entities.Cliente;
import com.helisha.inversiones7h.services.ClienteService;

import io.github.palexdev.materialfx.controls.MFXTextField;
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
@FxmlView("cliente.fxml")
public class ClienteController implements BootInitializable {

  @Autowired
  private FxWeaver fxWeaver;
  
  @Autowired
  private ClienteService clienteService; //inyectamos el servicio clienteService


  private ApplicationContext applicationContext; // contexto de spring

  @FXML
  private MFXTextField cedula;
  
  @FXML
  private MFXTextField nombreCliente;
  
  @FXML
  private MFXTextField apellidoCliente;
  
  @FXML
  private MFXTextField dirCliente;

  @FXML
  private MFXTextField tlfCliente;

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

  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  @FXML
  public void registrar(ActionEvent event) {

    String cedulaInput = cedula.getText();
    String nombreInput = nombreCliente.getText();
    String apellidoInput = apellidoCliente.getText();
    String dirInput = dirCliente.getText();
    String tlfInput = tlfCliente.getText();

    // Realiza las validaciones de los campos de que no existan
    if (cedulaInput.isEmpty() || nombreInput.isEmpty() || apellidoInput.isEmpty() || dirInput.isEmpty() || tlfInput.isEmpty()) {
      mostrarAlertaError("Error de registro", "Todos los campos son obligatorios", "Por favor, complete todos los campos antes de registrar el cliente.");
      return;
    }


    // Verificar si el cliente ya existe en la base de datos
    Cliente clienteExistente = clienteService.findByCedulaIdentidad(cedulaInput);

    //buscamos cliente por nombre y apellido
    //Cliente clienteNombre = clienteService.findByNombre(nombreInput);
    //Cliente clienteApellido = clienteService.findByApellido(apellidoInput);


    if (clienteExistente != null) {
        mostrarAlertaError("Error de registro", "Cliente ya registrado", "El cliente con la cédula ingresada ya está registrado en la base de datos.");
        return;
    }

    // Si pasa todas las validaciones, crea el nuevo cliente y guarda en la base de datos
    Cliente nuevoCliente = new Cliente();

    //Configuramos  los datos a insertar
    nuevoCliente.setCedulaIdentidad(cedulaInput);
    nuevoCliente.setNombre(nombreInput);
    nuevoCliente.setApellido(apellidoInput);
    nuevoCliente.setTelefono(tlfInput);
    nuevoCliente.setDireccion(dirInput);

    clienteService.save(nuevoCliente);

    // Limpia los campos de entrada después de registrar el cliente

    cedula.setText("");
    nombreCliente.setText("");
    apellidoCliente.setText("");
    dirCliente.setText("");
    tlfCliente.setText("");


  // Muestra un mensaje de éxito
  mostrarAlertaExito("Éxito", "Cliente registrado exitosamente");
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

  public void restablecer(){
    nombreCliente.clear();
    apellidoCliente.clear();
    cedula.clear();
    dirCliente.clear();
    tlfCliente.clear();
  }
    
}
