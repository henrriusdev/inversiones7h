package com.helisha.inversiones7h.controllers;

import com.helisha.inversiones7h.BootInitializable;
import com.helisha.inversiones7h.entities.Cliente;
import com.helisha.inversiones7h.entities.Producto;
import com.helisha.inversiones7h.entities.Venta;
import com.helisha.inversiones7h.services.ClienteService;
import com.helisha.inversiones7h.services.ProductoService;
import com.helisha.inversiones7h.services.VentaService;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.controls.models.spinner.IntegerSpinnerModel;
import io.github.palexdev.materialfx.effects.DepthLevel;
import io.github.palexdev.materialfx.enums.ButtonType;
import io.github.palexdev.materialfx.enums.FloatMode;
import io.github.palexdev.materialfx.filter.BigDecimalFilter;
import io.github.palexdev.materialfx.filter.LongFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import io.github.palexdev.materialfx.utils.others.FunctionalStringConverter;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.MFXValidator;
import io.github.palexdev.materialfx.validation.Validated;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
@FxmlView("venta.fxml")
public class VentaController implements BootInitializable {
  @Autowired
  private FxWeaver fxWeaver;

  @Autowired
  private ClienteService cService;

  @Autowired
  private ProductoService pService;

  @Autowired
  private VentaService vService;

  private ApplicationContext applicationContext; // contexto de spring

  private final MFXComboBox<Cliente> clienteCombo;

  private final MFXSpinner<Integer> cantidadSpinner;

  private final MFXButton agregarProductoBtn;

  private final MFXPaginatedTableView<Producto> tablaProductos;

  private MFXPaginatedTableView<Producto> tablaProductos2;

  private final MFXCheckbox checkbox;

  private final MFXComboBox<Producto> productosCombo;

  private final MFXTextField montoTotal;

  private List<Producto> productosVendidos;

  @FXML
  private MFXButton unlock;

  @FXML
  private MFXStepper stepper;

  private BigDecimal monto;

  public VentaController() {
    clienteCombo = new MFXComboBox<>();
    productosCombo = new MFXComboBox<>();
    agregarProductoBtn = new MFXButton();
    tablaProductos = new MFXPaginatedTableView<>();
    tablaProductos2 = new MFXPaginatedTableView<>();
    cantidadSpinner = new MFXSpinner<>();
    montoTotal = new MFXTextField();
    checkbox = new MFXCheckbox("Confirm Data?");
    productosVendidos = new ArrayList<>();
    monto = BigDecimal.valueOf(0);
  }


  @Override
  public void initialize(URL location, ResourceBundle resources) {

    refrescarCampos();

    // Utilizar un StringConverter para mostrar el label personalizado y usar el value interno para Cliente
    StringConverter<Cliente> clienteConverter = FunctionalStringConverter.to(cliente -> (cliente == null) ? "" : cliente.getCedulaIdentidad() + " - " + cliente.getNombre() + " " + cliente.getApellido());

    StringConverter<Producto> productoConverter = FunctionalStringConverter.to(producto -> (producto == null) ? "" : "#" + producto.getCodigo() + " - " + producto.getNombre());


    IntegerSpinnerModel model = new IntegerSpinnerModel(0);

    clienteCombo.setConverter(clienteConverter);
    clienteCombo.setFloatingText("Seleccione el cliente");
    clienteCombo.setFloatMode(FloatMode.BORDER);

    productosCombo.setConverter(productoConverter);
    productosCombo.setFloatingText("Seleccione el producto a agregar");
    productosCombo.setFloatMode(FloatMode.BORDER);

    productosCombo.valueProperty().addListener((obs, oldProducto, newProducto) -> {
      if (newProducto != null) {
        newProducto.setCantidadVendida(cantidadSpinner.getValue().longValue());
        model.setMax(newProducto.getCantidad().intValue() - (newProducto.getCantidadVendida() == 0 ? 0 : newProducto.getCantidadVendida().intValue()));
      }
    });

    cantidadSpinner.setSpinnerModel(model);
    cantidadSpinner.setValue(0);

    agregarProductoBtn.setText("Agregar producto");
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
    agregarProductoBtn.setOnAction(event -> {
      Producto producto = productosCombo.getValue();
      producto.setCantidadVendida(cantidadSpinner.getValue().longValue());

      productosVendidos.add(producto);
      tablaProductos.setItems(FXCollections.observableArrayList(productosVendidos));
      tablaProductos2.getItems().addAll(FXCollections.observableArrayList(productosVendidos));

      BigDecimal monto = productosVendidos.stream()
        .map(Producto::getPrecio)
        .reduce(BigDecimal.ZERO, BigDecimal::add);

      monto = monto.add(monto.multiply(BigDecimal.valueOf(0.16)));
      montoTotal.setText(monto.toString());

      cantidadSpinner.setValue(0);
    });

    montoTotal.setFloatingText("Monto total en $:");
    montoTotal.setFloatMode(FloatMode.BORDER);

    tablaProductos.setPrefWidth(799);
    tablaProductos.setPrefHeight(399);

    List<MFXStepperToggle> stepperToggles = createSteps();
    stepper.getStepperToggles().addAll(stepperToggles);

    unlock.visibleProperty().bind(stepper.mouseTransparentProperty());
    unlock.setOnAction(event -> stepper.setMouseTransparent(false));
  }

  private List<MFXStepperToggle> createSteps() {
    MFXStepperToggle step1 = new MFXStepperToggle("Agregar Cliente", new MFXFontIcon("fas-user-group", 16, Color.web("#f1c40f")));
    VBox step1Box = new VBox(20,clienteCombo);
    step1Box.setAlignment(Pos.CENTER);
    step1.setContent(step1Box);

    MFXStepperToggle step2 = new MFXStepperToggle("Añadir productos", new MFXFontIcon("fas-cart-shopping", 16, Color.web("#49a6d7")));

    HBox productBox = new HBox(10, productosCombo, cantidadSpinner, agregarProductoBtn);
    productBox.setAlignment(Pos.CENTER);

    configurarTabla(tablaProductos);

    tablaProductos.setPrefWidth(599);
    tablaProductos.setPrefHeight(399);

    tablaProductos.setItems(FXCollections.observableArrayList(productosVendidos));

    VBox step2Box = new VBox(20, productBox, montoTotal, tablaProductos);
    step2Box.setAlignment(Pos.CENTER);
    step2Box.setPrefWidth(800);
    step2Box.setPrefHeight(400);
    step2.setContent(step2Box);

    MFXStepperToggle step3 = new MFXStepperToggle("Confirmación", new MFXFontIcon("fas-check", 16, Color.web("#85CB33")));
    Node step3Grid = createGrid();
    step3.setContent(step3Grid);
    step3.getValidator().constraint("Los datos deben ser confirmados", checkbox.selectedProperty());

    return List.of(step1, step2, step3);
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

  private Node createGrid() {
    MFXTextField clienteLabel1 = createLabel("Cliente: ");
    MFXTextField clienteLabel2 = createLabel("");
    clienteLabel2.textProperty().bind(clienteCombo.textProperty());

    MFXTextField productoLabel = createLabel("Productos: ");
    tablaProductos.setPrefWidth(599);
    tablaProductos.setPrefHeight(399);

    MFXTextField montoTotalLabel1 = createLabel("Monto Total");
    MFXTextField montoTotalLabel2 = createLabel("");
    montoTotalLabel2.textProperty().bind(montoTotal.textProperty().concat("$"));

    clienteLabel1.getStyleClass().add("header-label");
    productoLabel.getStyleClass().add("header-label");
    montoTotalLabel1.getStyleClass().add("header-label");

    MFXTextField completedLabel = MFXTextField.asLabel("Completed!");
    completedLabel.getStyleClass().add("completed-label");

    HBox b1 = new HBox(clienteLabel1, clienteLabel2);

    tablaProductos2.getItems().addAll(FXCollections.observableArrayList(productosVendidos));

    configurarTabla(tablaProductos2);

    tablaProductos2.setPrefWidth(599);
    tablaProductos2.setPrefHeight(299);

    VBox b2 = new VBox(productoLabel, tablaProductos2);
    b2.setAlignment(Pos.CENTER);

    HBox b3 = new HBox(montoTotalLabel1, montoTotalLabel2);

    b1.setMaxWidth(Region.USE_PREF_SIZE);
    b2.setMaxWidth(Region.USE_PREF_SIZE);
    b3.setMaxWidth(Region.USE_PREF_SIZE);

    VBox box = new VBox(10, b1, b2, b3, checkbox);
    box.setAlignment(Pos.CENTER);

    StackPane.setAlignment(box, Pos.CENTER);

    stepper.setOnLastNext(event -> {
      Venta venta = new Venta();

      venta.setCliente(clienteCombo.getValue());
      productosVendidos = productosVendidos.stream().map(producto -> {
          Producto productoActualizado = new Producto();
          productoActualizado.setCodigo(producto.getCodigo());
          productoActualizado.setNombre(producto.getNombre());
          productoActualizado.setCantidad(producto.getCantidad() - producto.getCantidadVendida());
          productoActualizado.setPrecio(producto.getPrecio());
          productoActualizado.setProveedor(producto.getProveedorObj());
          productoActualizado.setCantidadVendida(producto.getCantidadVendida());
          return productoActualizado;
        })
        .collect(Collectors.toList());
      venta.setProductos(productosVendidos);
      venta.setMontoTotal(monto);

      vService.save(venta);

      box.getChildren().setAll(completedLabel);
      stepper.setMouseTransparent(true);
    });
    stepper.setOnBeforePrevious(event -> {
      if (stepper.isLastToggle()) {
        checkbox.setSelected(false);
        box.getChildren().setAll(b1, b2, b3, checkbox);
      }
    });

    return box;
  }

  public void refrescarCampos(){
    clienteCombo.getItems().addAll(cService.findAll());
    productosCombo.getItems().addAll(pService.findDisponibles());
  }

  private MFXTextField createLabel(String text) {
    MFXTextField label = MFXTextField.asLabel(text);
    label.setAlignment(Pos.CENTER_LEFT);
    label.setPrefWidth(200);
    label.setMinWidth(Region.USE_PREF_SIZE);
    label.setMaxWidth(Region.USE_PREF_SIZE);
    return label;
  }

  private void configurarTabla(MFXPaginatedTableView<Producto> tabla){
    MFXTableColumn<Producto> columnaProductos = new MFXTableColumn<>("Código", false, Comparator.comparing(Producto::getCodigo));
    MFXTableColumn<Producto> columnaNombre = new MFXTableColumn<>("Nombre", false, Comparator.comparing(Producto::getNombre));
    MFXTableColumn<Producto> columnaProveedor = new MFXTableColumn<>("Proveedor", false, Comparator.comparing(Producto::getProveedor));
    MFXTableColumn<Producto> columnaCantidad = new MFXTableColumn<>("Cantidad vendida", false, Comparator.comparing(Producto::getCantidadVendida));
    MFXTableColumn<Producto> columnaPrecio = new MFXTableColumn<>("Precio", false, Comparator.comparing(Producto::getPrecioString));

    columnaProductos.setRowCellFactory(producto -> new MFXTableRowCell<>(Producto::getCodigo));
    columnaNombre.setRowCellFactory(producto -> new MFXTableRowCell<>(Producto::getNombre));
    columnaProveedor.setRowCellFactory(producto -> new MFXTableRowCell<>(Producto::getProveedor));
    columnaPrecio.setRowCellFactory(producto -> new MFXTableRowCell<>(Producto::getPrecioString));
    columnaCantidad.setRowCellFactory(producto -> new MFXTableRowCell<>(Producto::getCantidadVendida));

    tabla.getTableColumns().setAll(columnaProductos,columnaNombre,columnaProveedor,columnaCantidad, columnaPrecio);
    tabla.getFilters().setAll(
      new StringFilter<>("Código",Producto::getCodigo),
      new StringFilter<>("Nombre", Producto::getNombre),
      new BigDecimalFilter<>("Precio", Producto::getPrecio),
      new LongFilter<>("Cantidad vendida", Producto::getCantidadVendida),
      new StringFilter<>("Proveedor", Producto::getProveedor)
    );
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
