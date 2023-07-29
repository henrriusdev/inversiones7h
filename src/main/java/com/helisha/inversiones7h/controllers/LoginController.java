package com.helisha.inversiones7h.controllers;

import com.helisha.inversiones7h.BootInitializable;
import com.helisha.inversiones7h.MainApp;
import com.helisha.inversiones7h.entities.User;
import com.helisha.inversiones7h.services.IUserService;
import fr.brouillard.oss.cssfx.CSSFX;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
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

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    Date fecha=new Date();
    SimpleDateFormat formatoFecha =new SimpleDateFormat("dd/MM/yyyy");

    String usuario = userNameField.getText();
    String clave = passwordField.getText();

    if (!uService.existsById(usuario)) {
      mostrarAlerta("Oh no!", "Los datos ingresados puede que sean erróneos, se recomienda revisar y probar nuevamente");
      return;
    }

    User usuarioEncontrado = uService.findById(usuario);
    usuarioEncontrado.setFechaIngreso(formatoFecha.format(fecha));


    if (!BCrypt.checkpw(clave, usuarioEncontrado.getClave())) {
      mostrarAlerta("Oh no!", "Los datos ingresados puede que sean erróneos, se recomienda revisarlos y probar nuevamente");
      return;
    }

    Parent root;
    Stage stage = new Stage();
    if (usuarioEncontrado.getRol().equals(User.Rol.ADMIN)) {
      root = fxWeaver.loadView(SupervisorController.class);
      stage.setTitle("Panel del Supervisor - Inversiones7H");

      SupervisorController supervisorController = springContext.getBean(SupervisorController.class);
      supervisorController.setStage(stage);
    } else {
      root = fxWeaver.loadView(CajaController.class);
      stage.setTitle("Panel del Cajero - Inversiones7H");

      CajaController cajaController = springContext.getBean(CajaController.class);
      cajaController.setStage(stage);
    }

    try {
      CSSFX.start();
      Scene scene = new Scene(root);
      MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
      scene.setFill(Color.TRANSPARENT);
      stage.setScene(scene);
      stage.initStyle(StageStyle.TRANSPARENT);
      stage.getIcons().add(new Image(MainApp.class.getResourceAsStream("logo.png")));
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
    return null;
  }

  @Override
  public void initValidator() {

  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }
}
