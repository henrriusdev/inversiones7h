package com.hbourgeot.inversiones7h.controllers;

import com.hbourgeot.inversiones7h.BootInitializable;
import com.hbourgeot.inversiones7h.entities.Cliente;
import com.hbourgeot.inversiones7h.entities.Producto;
import com.hbourgeot.inversiones7h.services.ClienteService;
import com.hbourgeot.inversiones7h.services.ProductoService;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.effects.DepthLevel;
import io.github.palexdev.materialfx.enums.ButtonType;
import io.github.palexdev.materialfx.utils.others.FunctionalStringConverter;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.MFXValidator;
import io.github.palexdev.materialfx.validation.Validated;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
@FxmlView("venta.fxml")
public class VentaController implements BootInitializable {
  @Autowired
  private FxWeaver fxWeaver;

  @Autowired
  private ClienteService cService;

  @Autowired
  private ProductoService pService;

  private ApplicationContext applicationContext; // contexto de spring

  private final MFXComboBox<Cliente> clienteCombo;


  private final MFXButton agregarProductoBtn;

  private final MFXPaginatedTableView<Producto> tablaProductos;

  private final MFXCheckbox checkbox;

  private final MFXComboBox<Producto> productosCombo;

  @FXML
  private MFXButton unlock;

  @FXML
  private MFXStepper stepper;

  public VentaController() {
    clienteCombo = new MFXComboBox<>();
    productosCombo = new MFXComboBox<>();
    agregarProductoBtn = new MFXButton();
    tablaProductos = new MFXPaginatedTableView<>();
    checkbox = new MFXCheckbox("Confirm Data?");
  }


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Cliente cliente = new Cliente();
    cliente.setCedulaIdentidad("V-30555724");
    cliente.setNombre("Henrry");
    cliente.setApellido("Bourgeot");

    clienteCombo.getItems().addAll(cService.findAll());
    productosCombo.getItems().addAll(pService.findDisponibles());

    // Utilizar un StringConverter para mostrar el label personalizado y usar el value interno para Cliente
    StringConverter<Cliente> clienteConverter = FunctionalStringConverter.to(clien -> (clien == null) ? "" : clien.getCedulaIdentidad() + " - " + clien.getNombre() + " " + clien.getApellido());

    StringConverter<Producto> productoConverter = FunctionalStringConverter.to(produc -> (produc == null) ? "" : "#" + produc.getCodigo() + " - " + produc.getNombre());

    clienteCombo.setConverter(clienteConverter);
    productosCombo.setConverter(productoConverter);

    agregarProductoBtn.setText("Agregar producto");
    agregarProductoBtn.setCursor(Cursor.HAND);
    agregarProductoBtn.setRippleColor(Color.DIMGRAY);
    agregarProductoBtn.setRippleAnimateBackground(true);
    agregarProductoBtn.setRippleBackgroundOpacity(0.3);
    agregarProductoBtn.setRippleAnimationSpeed(1);
    agregarProductoBtn.setRippleRadius(25);
    agregarProductoBtn.setRippleRadiusMultiplier(2);
    agregarProductoBtn.setButtonType(ButtonType.RAISED);
    agregarProductoBtn.setDepthLevel(DepthLevel.LEVEL3);
    agregarProductoBtn.setPadding(new Insets(10,10,10,10));
    agregarProductoBtn.setId("custom");


    tablaProductos.setItems(FXCollections.observableArrayList(new Producto()));
    tablaProductos.setPrefWidth(799);
    tablaProductos.setPrefHeight(399);
    tablaProductos.setRowsPerPage(8);


    List<MFXStepperToggle> stepperToggles = createSteps();
    stepper.getStepperToggles().addAll(stepperToggles);

    unlock.visibleProperty().bind(stepper.mouseTransparentProperty());
    unlock.setOnAction(event -> stepper.setMouseTransparent(false));
  }

  private List<MFXStepperToggle> createSteps() {
    MFXStepperToggle step1 = new MFXStepperToggle("Cliente", new MFXFontIcon("fas-user-group", 16, Color.web("#f1c40f")));
    VBox step1Box = new VBox(20,clienteCombo);
    step1Box.setAlignment(Pos.CENTER);
    step1.setContent(step1Box);

    MFXStepperToggle step2 = new MFXStepperToggle("Añadir productos", new MFXFontIcon("fas-cart-shopping", 16, Color.web("#49a6d7")));

    HBox productBox = new HBox(productosCombo, agregarProductoBtn);
    productBox.setAlignment(Pos.CENTER);
    productBox.setSpacing(10);
    VBox step2Box = new VBox(20, productBox, tablaProductos);
    step2Box.setAlignment(Pos.CENTER);
    step2Box.setPrefWidth(800);
    step2Box.setPrefHeight(400);
    step2.setContent(step2Box);

    //MFXStepperToggle step3 = new MFXStepperToggle("Step 3", new MFXFontIcon("fas-check", 16, Color.web("#85CB33")));
    //Node step3Grid = createGrid();
    //step3.setContent(step3Grid);
    //step3.getValidator().constraint("Data must be confirmed", checkbox.selectedProperty());

    return List.of(step1, step2);
  }

  private <T extends Node & Validated> Node wrapNodeForValidation(T node) {
    Label errorLabel = new Label();
    errorLabel.getStyleClass().add("error-label");
    errorLabel.setManaged(false);
    stepper.addEventHandler(MFXStepper.MFXStepperEvent.VALIDATION_FAILED_EVENT, event -> {
      MFXValidator validator = node.getValidator();
      List<Constraint> validate = validator.validate();
      if (!validate.isEmpty()) {
        errorLabel.setText(validate.get(0).getMessage());
      }
    });
    stepper.addEventHandler(MFXStepper.MFXStepperEvent.NEXT_EVENT, event -> errorLabel.setText(""));
    VBox wrap = new VBox(3, node, errorLabel) {
      @Override
      protected void layoutChildren() {
        super.layoutChildren();

        double x = node.getBoundsInParent().getMinX();
        double y = node.getBoundsInParent().getMaxY() + getSpacing();
        double width = getWidth();
        double height = errorLabel.prefHeight(-1);
        errorLabel.resizeRelocate(x, y, width, height);
      }

      @Override
      protected double computePrefHeight(double width) {
        return super.computePrefHeight(width) + errorLabel.getHeight() + getSpacing();
      }
    };
    wrap.setAlignment(Pos.CENTER);
    return wrap;
  }

  /*private Node createGrid() {
    MFXTextField usernameLabel1 = createLabel("Username: ");
    MFXTextField usernameLabel2 = createLabel("");
    usernameLabel2.textProperty().bind(loginField.textProperty());

    MFXTextField firstNameLabel1 = createLabel("First Name: ");
    MFXTextField firstNameLabel2 = createLabel("");
    firstNameLabel2.textProperty().bind(firstNameField.textProperty());

    MFXTextField lastNameLabel1 = createLabel("Last Name: ");
    MFXTextField lastNameLabel2 = createLabel("");
    lastNameLabel2.textProperty().bind(lastNameField.textProperty());

    MFXTextField genderLabel1 = createLabel("Gender: ");
    MFXTextField genderLabel2 = createLabel("");
    genderLabel2.textProperty().bind(Bindings.createStringBinding(
      () -> genderCombo.getValue() != null ? genderCombo.getValue() : "",
      genderCombo.valueProperty()
    ));

    usernameLabel1.getStyleClass().add("header-label");
    firstNameLabel1.getStyleClass().add("header-label");
    lastNameLabel1.getStyleClass().add("header-label");
    genderLabel1.getStyleClass().add("header-label");

    MFXTextField completedLabel = MFXTextField.asLabel("Completed!");
    completedLabel.getStyleClass().add("completed-label");

    HBox b1 = new HBox(usernameLabel1, usernameLabel2);
    HBox b2 = new HBox(firstNameLabel1, firstNameLabel2);
    HBox b3 = new HBox(lastNameLabel1, lastNameLabel2);
    HBox b4 = new HBox(genderLabel1, genderLabel2);

    b1.setMaxWidth(Region.USE_PREF_SIZE);
    b2.setMaxWidth(Region.USE_PREF_SIZE);
    b3.setMaxWidth(Region.USE_PREF_SIZE);
    b4.setMaxWidth(Region.USE_PREF_SIZE);

    VBox box = new VBox(10, b1, b2, b3, b4, checkbox);
    box.setAlignment(Pos.CENTER);
    StackPane.setAlignment(box, Pos.CENTER);

    stepper.setOnLastNext(event -> {
      box.getChildren().setAll(completedLabel);
      stepper.setMouseTransparent(true);
    });
    stepper.setOnBeforePrevious(event -> {
      if (stepper.isLastToggle()) {
        checkbox.setSelected(false);
        box.getChildren().setAll(b1, b2, b3, b4, checkbox);
      }
    });

    return box;
  }*/

  private MFXTextField createLabel(String text) {
    MFXTextField label = MFXTextField.asLabel(text);
    label.setAlignment(Pos.CENTER_LEFT);
    label.setPrefWidth(200);
    label.setMinWidth(Region.USE_PREF_SIZE);
    label.setMaxWidth(Region.USE_PREF_SIZE);
    return label;
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
}
