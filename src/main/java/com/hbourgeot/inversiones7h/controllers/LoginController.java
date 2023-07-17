package com.hbourgeot.inversiones7h.controllers;

import com.hbourgeot.inversiones7h.BootInitializable;
import com.hbourgeot.inversiones7h.entities.User;
import com.hbourgeot.inversiones7h.services.IUserService;
import com.hbourgeot.inversiones7h.utils.Screens;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


@Component
@FxmlView("Login.fxml")
public class LoginController implements BootInitializable {

  @FXML
  private MFXPasswordField passwordField;

  @FXML
  private MFXTextField userNameField;

  @FXML
  private MFXButton loginBtn;

  @Autowired
  public IUserService uService;

  @Autowired
  private FxWeaver fxWeaver;

  private ApplicationContext springContext;

  @FXML
  public void iniciarSesion(ActionEvent event) {
    String usuario = userNameField.getText();
    String clave = passwordField.getText();

    if (!uService.existsById(usuario)) {
      mostrarAlerta("Oh no!", "Los datos ingresados puede que sean erróneos, se recomienda revisar y probar nuevamente");
      return;
    }

    User usuarioEncontrado = uService.findById(usuario);
    System.out.println("Clave correcta: " + BCrypt.checkpw(clave, usuarioEncontrado.getClave()));
    if (!BCrypt.checkpw(clave, usuarioEncontrado.getClave())) {
      mostrarAlerta("Oh no!", "Los datos ingresados puede que sean erróneos, se recomienda revisarlos y probar nuevamente");
      return;
    }

    Parent root;
    Stage stage = new Stage();
    if (usuarioEncontrado.getRol().equals(User.Rol.ADMIN)) {
      root = fxWeaver.loadView(SupervisorController.class);
      stage.setTitle("Panel del Supervisor - Inversiones7H");
    } else {
      root = fxWeaver.loadView(CajaController.class);
      stage.setTitle("Panel del Cajero - Inversiones7H");
    }

    try {
      Scene scene = new Scene(root);
      scene.setFill(Color.TRANSPARENT);
      stage.setScene(scene);
      stage.initStyle(StageStyle.TRANSPARENT);
      stage.show();
    } catch (Exception exception) {
      System.out.println(exception.getLocalizedMessage());
    }

    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    currentStage.close();
  }

  private void mostrarAlerta(String titulo, String mensaje) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(titulo);
    alert.setHeaderText(null);
    alert.setContentText(mensaje);
    alert.showAndWait();
  }

  @Override
  public void initConstruct() {

  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.springContext = applicationContext;
  }

  @Override
  public void stage(Stage primaryStage) {

  }

  @Override
  public Node initView() {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource(Screens.Login));
      loader.setController(LoginController.class);
      return loader.load();
    } catch (IOException e){
      System.err.println("can't load");
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public void initValidator() {

  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }
}
