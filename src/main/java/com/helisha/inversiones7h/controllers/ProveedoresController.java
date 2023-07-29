package com.helisha.inversiones7h.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.helisha.inversiones7h.BootInitializable;
import com.helisha.inversiones7h.entities.Proveedor;
import com.helisha.inversiones7h.services.ProveedorService;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;



@Component
@FxmlView("proveedor.fxml")
public class ProveedoresController implements BootInitializable  {

  @Autowired
  private FxWeaver fxWeaver;

  @Autowired
  private ProveedorService ProveedorService;

  @FXML
  private MFXTextField cedula;


  @FXML
  private MFXTextField nombre;

  @FXML
  private MFXTextField categoria;

  @FXML
  private MFXTextField ubicacion;

  @FXML
  private MFXButton registrarBtn;



    @Override
    public void initConstruct() {
        
    }

    @Override
    public void initValidator() {
        
    }

    @Override
    public Node initView() {
        return null;
    }

    @Override
    public void stage(Stage primaryStage) {
       
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
      
    }

    @FXML
    public void registrarProveedor(ActionEvent event){
        
        String cedulaInput = cedula.getText();
        String nombreInput = nombre.getText();
        String categoriaInput = categoria.getText();
        String ubicacionInput = ubicacion.getText();

        // Realiza las validaciones de los campos de que no existan

    if(cedulaInput.isEmpty() || nombreInput.isEmpty()|| categoriaInput.isEmpty()|| ubicacionInput.isEmpty()){

        mostrarAlertaError("Error de registro", "Todos los campos son obligatorios", "Por favor, complete todos los campos antes de registrar el cliente.");
          return;
      }

      //verificamos si ya se registro ese proveedor

      Proveedor proveedorExiste = ProveedorService.findByCedulaIdentidad(cedulaInput);


      if(proveedorExiste != null){
        mostrarAlertaError("Error de registro", "Proveedor ya registrado", "El proveedor con la cédula ingresada ya está registrado en la base de datos.");
        return;
      }

      // en caso de que todo funcione correctamente registra un nuevo proveedor

      Proveedor proveedor = new Proveedor();

      proveedor.setCedulaIdentidad(cedulaInput);
      proveedor.setNombre(nombreInput);
      proveedor.setCategoria(Proveedor.Categoria.Accesorios);
      proveedor.setUbicacion(ubicacionInput);

      ProveedorService.save(proveedor);

      //limpiamos campos 

    cedula.setText("");
    nombre.setText("");
    ubicacion.clear();
    categoria.clear();

    // Muestra un mensaje de éxito
  mostrarAlertaExito("Éxito", "Proveedor registrado exitosamente");

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
