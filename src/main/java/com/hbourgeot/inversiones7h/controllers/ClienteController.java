package com.hbourgeot.inversiones7h.controllers;

import com.hbourgeot.inversiones7h.BootInitializable;
import com.hbourgeot.inversiones7h.entities.Cliente;
import com.hbourgeot.inversiones7h.services.IClienteService;
import io.github.palexdev.materialfx.controls.MFXTextField;
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

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("cliente.fxml")
public class ClienteController implements BootInitializable {

  @Autowired
  private FxWeaver fxWeaver;

  @Autowired
  private IClienteService clienteService; // inyectamos el servicio ClienteService

  private ApplicationContext applicationContext; // contexto de spring

  @FXML
  private MFXTextField cedula;

  @FXML
  private MFXTextField nombreCliente;

  @FXML
  private MFXTextField apellidoCliente;

  @FXML
  private MFXTextField tlfCliente;

  @FXML
  private MFXTextField dirCliente;

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
  public void registrar(ActionEvent event){
    Cliente cliente = new Cliente();

    String cedulaInput = cedula.getText();
    String nombreInput = nombreCliente.getText();
    String apellidoInput = apellidoCliente.getText();
    String dirInput = dirCliente.getText();
    String tlfInput = tlfCliente.getText();

    cliente.setCedulaIdentidad(cedulaInput);
    cliente.setNombre(nombreInput);
    cliente.setApellido(apellidoInput);
    cliente.setDireccion(dirInput);
    cliente.setTelefono(tlfInput);

    clienteService.save(cliente);
  }
}
