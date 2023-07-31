package com.helisha.inversiones7h.controllers;

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

@Component
@FxmlView("Caja.fxml")
public class CajaController implements BootInitializable {
  private Stage stage;
  private double xOffset;
  private double yOffset;
  private final ToggleGroup toggleGroup;

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

  @Autowired
  private FxWeaver fxWeaver;

  private ApplicationContext applicationContext; // contexto de spring

  public CajaController() {
    this.toggleGroup = new ToggleGroup();
    ToggleButtonsUtil.addAlwaysOneSelectedSupport(toggleGroup);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    closeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> Platform.exit());
    minimizeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> ((Stage) rootPane.getScene().getWindow()).setIconified(true));
    alwaysOnTopIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
      boolean newVal = !stage.isAlwaysOnTop();
      alwaysOnTopIcon.pseudoClassStateChanged(PseudoClass.getPseudoClass("always-on-top"), newVal);
      stage.setAlwaysOnTop(newVal);
    });

    windowHeader.setOnMousePressed(event -> {
      xOffset = stage.getX() - event.getScreenX();
      yOffset = stage.getY() - event.getScreenY();
    });
    windowHeader.setOnMouseDragged(event -> {
      stage.setX(event.getScreenX() + xOffset);
      stage.setY(event.getScreenY() + yOffset);
    });

    initializeLoader();

    ScrollUtils.addSmoothScrolling(scrollPane);

    // The only way to get a fucking smooth image in this shitty framework
    Image image = new Image(MainApp.class.getResourceAsStream("logo_alt.png"), 64, 64, true, true);
    ImageView logo = new ImageView(image);
    Circle clip = new Circle(30);
    clip.centerXProperty().set(logo.layoutBoundsProperty().get().getCenterX());
    clip.centerYProperty().set(logo.layoutBoundsProperty().get().getCenterY());
    logo.setClip(clip);
    logoContainer.getChildren().add(logo);
  }

  private void initializeLoader() {
    Parent ventaController = fxWeaver.loadView(VentaController.class); //cargamos vista
    Parent clienteController = fxWeaver.loadView(ClienteController.class); //cargamos vista
    Parent bienvenidaCaja = fxWeaver.loadView(BienvenidaCajeroController.class);
    Parent verCliente = fxWeaver.loadView(VerClienteController.class);

    // botones
    ToggleButton ventaBtn = createToggle("fas-shop", "Generar Venta");
    ToggleButton clienteBtn = createToggle("fas-user-group", "Agregar Cliente");
    bienvenidaBtn = createToggle("fas-shop", "Inicio");
    ToggleButton verClienteBtn = createToggle("fas-users-viewfinder", "Ver y editar clientes");

    // eventos
    ventaBtn.setOnAction(event -> {
      contentPane.getChildren().setAll(ventaController);
      // cuando se haga click, mostraremos la vista de compra
    });

    clienteBtn.setOnAction(event -> {
      contentPane.getChildren().setAll(clienteController);
      // cuando se haga click, mostraremos la vista de productos
    });

    bienvenidaBtn.setOnAction(event -> {
      contentPane.getChildren().setAll(bienvenidaCaja);
      // cuando se haga click, mostraremos la vista de productos
    });

    verClienteBtn.setOnAction(event -> {
      VerClienteController verClienteController = applicationContext.getBean(VerClienteController.class);
      verClienteController.recargarTabla();
      contentPane.getChildren().setAll(verCliente);
      // cuando se haga click, mostraremos la vista de productos
    });

    // añadimos al navbar
    navBar.getChildren().add(bienvenidaBtn);
    navBar.getChildren().add(ventaBtn);
    navBar.getChildren().add(clienteBtn);
    navBar.getChildren().add(verClienteBtn);

    contentPane.getChildren().setAll(bienvenidaCaja);


  }

  private ToggleButton createToggle(String icon, String text) {
    return createToggle(icon, text, 0);
  }

  private ToggleButton createToggle(String icon, String text, double rotate) {
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
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  @FXML
  public void volverAlInicio() {
    bienvenidaBtn.setSelected(true);
    Parent bienvenidaCaja = fxWeaver.loadView(BienvenidaCajeroController.class);
    contentPane.getChildren().setAll(bienvenidaCaja);
  }

  public void salir() {
    Platform.exit();
  }

  @FXML
  public void cerrarSesion(ActionEvent event) {
    Parent root = fxWeaver.loadView(LoginViewController.class);
    Stage stage = new Stage();
    stage.setTitle("Inicio de sesión - Inversiones7H");
    LoginViewController loginViewController = applicationContext.getBean(LoginViewController.class);
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

    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    currentStage.close();

    VerClienteController verClienteController = applicationContext.getBean(VerClienteController.class);
    verClienteController.restablecer();

    ClienteController clienteController = applicationContext.getBean(ClienteController.class);
    clienteController.restablecer();

    VentaController ventaController = applicationContext.getBean(VentaController.class);
    ventaController.restablecer();
  }
}
