package com.helisha.inversiones7h.controllers;

import com.helisha.inversiones7h.BootInitializable;
import com.helisha.inversiones7h.entities.Cliente;
import com.helisha.inversiones7h.services.ClienteService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.utils.others.FunctionalStringConverter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
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
  private MFXListView<Cliente> listaClientes;

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
    recargarLista();
  }

  public void recargarLista(){
    StringConverter<Cliente> converter = FunctionalStringConverter.to(cliente -> (cliente == null) ? "" : "Cédula: " + cliente.getCedulaIdentidad() + " - Nombre Completo: " + cliente.getNombre() + " " + cliente.getApellido() );
    listaClientes.setItems(FXCollections.observableArrayList(cService.findAll()));

    listaClientes.setConverter(converter);
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

  }
}
