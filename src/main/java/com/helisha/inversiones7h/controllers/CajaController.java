package com.helisha.inversiones7h.controllers;

// imports
import com.helisha.inversiones7h.BootInitializable;
import com.helisha.inversiones7h.MainApp;
import fr.brouillard.oss.cssfx.CSSFX;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXIconWrapper;
import io.github.palexdev.materialfx.controls.MFXRectangleToggleNode;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import io.github.palexdev.materialfx.utils.ScrollUtils;
import io.github.palexdev.materialfx.utils.ToggleButtonsUtil;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component // denota que es un componente de spring boot
@FxmlView("Caja.fxml") // denotamos vista
public class CajaController implements BootInitializable {

  // variables
  private Stage stage;
  private double xOffset;
  private double yOffset;
  private final ToggleGroup toggleGroup;

  // Variables provenientes del archivo FXML
  @FXML
  private HBox windowHeader;

  @FXML
  private MFXFontIcon closeIcon;

  @FXML
  private MFXFontIcon minimizeIcon;

  @FXML
  private MFXFontIcon alwaysOnTopIcon;

  @FXML
  private AnchorPane rootPane;

  @FXML
  private MFXScrollPane scrollPane;

  @FXML
  private VBox navBar;

  @FXML
  private StackPane contentPane;

  @FXML
  private StackPane logoContainer;

  @FXML
  private MFXButton inicioBtn;

  @FXML
  private MFXButton salirBtn;

  @FXML
  private MFXButton cerrarSesionBtn;

  private ToggleButton bienvenidaBtn;

  @Autowired // inyeccion de dependencias
  private FxWeaver fxWeaver;

  private ApplicationContext applicationContext; // contexto de spring

  public CajaController() { // definicion de variables
    this.toggleGroup = new ToggleGroup();
    ToggleButtonsUtil.addAlwaysOneSelectedSupport(toggleGroup);
  }

  @Override // funcion que se ejecuta al inicializar el controlador
  public void initialize(URL location, ResourceBundle resources) {
    // eventos para los botones de la esquina superior derecha
    closeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> Platform.exit()); //cerrar
    minimizeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> ((Stage) rootPane.getScene().getWindow()).setIconified(true));
    alwaysOnTopIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
      boolean newVal = !stage.isAlwaysOnTop();
      alwaysOnTopIcon.pseudoClassStateChanged(PseudoClass.getPseudoClass("always-on-top"), newVal);
      stage.setAlwaysOnTop(newVal);
    });

    // eventos para la ventana completa
    windowHeader.setOnMousePressed(event -> {
      xOffset = stage.getX() - event.getScreenX();
      yOffset = stage.getY() - event.getScreenY();
    });

    windowHeader.setOnMouseDragged(event -> {
      stage.setX(event.getScreenX() + xOffset);
      stage.setY(event.getScreenY() + yOffset);
    });

    initializeLoader(); // cargar opciones

    ScrollUtils.addSmoothScrolling(scrollPane);

    // cargar logo
    Image image = new Image(MainApp.class.getResourceAsStream("logo_alt.png"), 64, 64, true, true);
    ImageView logo = new ImageView(image);
    Circle clip = new Circle(30);
    clip.centerXProperty().set(logo.layoutBoundsProperty().get().getCenterX());
    clip.centerYProperty().set(logo.layoutBoundsProperty().get().getCenterY());
    logo.setClip(clip);
    logoContainer.getChildren().add(logo);
  }

  private void initializeLoader() {
    // cargar las vistas de las opciones del menu
    Parent ventaVista = fxWeaver.loadView(VentaController.class); //cargamos vista
    Parent clienteVista = fxWeaver.loadView(ClienteController.class); //cargamos vista
    Parent bienvenidaCaja = fxWeaver.loadView(BienvenidaCajeroController.class);
    Parent verClienteVista = fxWeaver.loadView(VerClienteController.class);

    // opciones del menu
    ToggleButton ventaBtn = createToggle("fas-shop", "Generar Venta");
    ToggleButton clienteBtn = createToggle("fas-user-group", "Agregar Cliente");
    bienvenidaBtn = createToggle("fas-shop", "Inicio");
    ToggleButton verClienteBtn = createToggle("fas-users-viewfinder", "Ver y editar clientes");

    // eventos
    ventaBtn.setOnAction(event -> {
      VentaController ventaController = applicationContext.getBean(VentaController.class); // obtenemos la clase cargada
      ventaController.refrescarCampos();// recargamos tabla
      ventaController.restablecer();
      contentPane.getChildren().setAll(ventaVista);
      // cuando se haga click, mostraremos la vista de compra
    });

    clienteBtn.setOnAction(event -> {
      contentPane.getChildren().setAll(clienteVista);
      // cuando se haga click, mostraremos la vista de productos
    });

    bienvenidaBtn.setOnAction(event -> {
      contentPane.getChildren().setAll(bienvenidaCaja);
      // cuando se haga click, mostraremos la vista de productos
    });

    verClienteBtn.setOnAction(event -> {
      VerClienteController verClienteController = applicationContext.getBean(VerClienteController.class); // obtenemos la clase cargada
      verClienteController.recargarTabla(); // recargamos tabla
      contentPane.getChildren().setAll(verClienteVista);
      // cuando se haga click, mostraremos la vista de productos
    });

    // añadimos al navbar
    navBar.getChildren().add(bienvenidaBtn);
    navBar.getChildren().add(ventaBtn);
    navBar.getChildren().add(clienteBtn);
    navBar.getChildren().add(verClienteBtn);

    contentPane.getChildren().setAll(bienvenidaCaja); // por defecto se vera la bienvenida
  }

  private ToggleButton createToggle(String icon, String text) {
    return createToggle(icon, text, 0);
  }

  private ToggleButton createToggle(String icon, String text, double rotate) { // funcion para crear la opcion para el menu
    MFXIconWrapper wrapper = new MFXIconWrapper(icon, 24, 32);
    MFXRectangleToggleNode toggleNode = new MFXRectangleToggleNode(text, wrapper);
    toggleNode.setAlignment(Pos.CENTER_LEFT);
    toggleNode.setMaxWidth(Double.MAX_VALUE);
    toggleNode.setToggleGroup(toggleGroup);
    if (rotate != 0) wrapper.getIcon().setRotate(rotate);
    return toggleNode;
  }

  public void setStage(Stage stage) {
    this.stage = stage;
  }

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
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException { // contexto de la app
    this.applicationContext = applicationContext;
  }

  @FXML
  public void volverAlInicio() { // accion para volver al inicio
    bienvenidaBtn.setSelected(true);
    Parent bienvenidaCaja = fxWeaver.loadView(BienvenidaCajeroController.class);
    contentPane.getChildren().setAll(bienvenidaCaja);
  }

  public void salir() {
    Platform.exit();
  }

  @FXML
  public void cerrarSesion(ActionEvent event) { // accion para cerrar la sesion
    Parent root = fxWeaver.loadView(LoginViewController.class); // cargamos vista
    Stage stage = new Stage();
    stage.setTitle("Inicio de sesión - Inversiones7H"); // titulo
    LoginViewController loginViewController = applicationContext.getBean(LoginViewController.class); // obtenemos la clase cargada
    loginViewController.setStage(stage);

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

    // obtenemos la ventana actual y la cerramos
    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    currentStage.close();

    // restablecemos los campos de cada controller
    VerClienteController verClienteController = applicationContext.getBean(VerClienteController.class);
    verClienteController.restablecer();

    ClienteController clienteController = applicationContext.getBean(ClienteController.class);
    clienteController.restablecer();

    VentaController ventaController = applicationContext.getBean(VentaController.class);
    ventaController.restablecer();
  }
}
