package com.hbourgeot.inversiones7h.controllers;

// imports
import com.hbourgeot.inversiones7h.BootInitializable;
import com.hbourgeot.inversiones7h.MainApp;
import io.github.palexdev.materialfx.controls.MFXIconWrapper;
import io.github.palexdev.materialfx.controls.MFXRectangleToggleNode;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.utils.ScrollUtils;
import io.github.palexdev.materialfx.utils.ToggleButtonsUtil;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component // definimos un componente de spring
@FxmlView("Supervisor.fxml") // Supervisor.fxml es la vista de este controlador
public class SupervisorController implements BootInitializable { // implementamos una interfaz

	// variables del programa
	private Stage stage;
	private double xOffset;
	private double yOffset;
	private final ToggleGroup toggleGroup;

	@FXML // las variables que tengan al decorador @FXML es porque provienen de la vista
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

	@Autowired
	private FxWeaver fxWeaver;

	private ApplicationContext applicationContext; // contexto de spring

	public SupervisorController() { // constructor
		this.toggleGroup = new ToggleGroup();
		ToggleButtonsUtil.addAlwaysOneSelectedSupport(toggleGroup);
		// el codigo de arriba permitirá hacer click en un solo botón y no en varios
	}

	@Override
	// función que se ejecutará al inicializar la vista
	public void initialize(URL location, ResourceBundle resources) {

		// eventos para el panel superior
		closeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> Platform.exit()); // salida
		minimizeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> ((Stage) rootPane.getScene().getWindow()).setIconified(true)); // minimizar
		alwaysOnTopIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			boolean newVal = !stage.isAlwaysOnTop();
			alwaysOnTopIcon.pseudoClassStateChanged(PseudoClass.getPseudoClass("always-on-top"), newVal);
			stage.setAlwaysOnTop(newVal);
			// botón para tener siempre el programa encima de todo
		});

		windowHeader.setOnMousePressed(event -> {
			xOffset = stage.getX() - event.getScreenX();
			yOffset = stage.getY() - event.getScreenY();
		});

		windowHeader.setOnMouseDragged(event -> {
			stage.setX(event.getScreenX() + xOffset);
			stage.setY(event.getScreenY() + yOffset);
		});

		// como es un programa totalmente personalizado, necesitamos realizar este código para
		// poder permitir mover la ventana a cualquier sitio de la pantalla

		initializeLoader(); // inicializamos las opciones

		ScrollUtils.addSmoothScrolling(scrollPane);
		// añadimos un scroll suave al panel de las opciones

		// diseño del logo
		Image image = new Image(MainApp.class.getResourceAsStream("logo_alt.png"), 64, 64, true, true);
		ImageView logo = new ImageView(image);
		Circle clip = new Circle(30);
		clip.centerXProperty().set(logo.layoutBoundsProperty().get().getCenterX());
		clip.centerYProperty().set(logo.layoutBoundsProperty().get().getCenterY());
		logo.setClip(clip);
		logoContainer.getChildren().add(logo);
	}

	// inicializar las opciones de navegación
	private void initializeLoader() {
		Parent compraController = fxWeaver.loadView(CompraController.class); //cargamos vista
		Parent productosController = fxWeaver.loadView(ProductosController.class); //cargamos vista

		// botones
		ToggleButton compraBtn = createToggle("fas-circle-dot", "Generar Compra");
		ToggleButton productosBtn = createToggle("fas-icons", "Agregar Producto");

		// eventos
		compraBtn.setOnAction(event -> {
			contentPane.getChildren().setAll(compraController);
			// cuando se haga click, mostraremos la vista de compra
		});

		productosBtn.setOnAction(event -> {
			contentPane.getChildren().setAll(productosController);
			// cuando se haga click, mostraremos la vista de productos
		});

		// añadimos al navbar
		navBar.getChildren().add(compraBtn);
		navBar.getChildren().add(productosBtn);

		contentPane.getChildren().setAll(productosController);
	}

	private ToggleButton createToggle(String icono, String texto) {
		return createToggle(icono, texto, 0);
	}

	private ToggleButton createToggle(String icono, String texto, double rotate) {
		MFXIconWrapper wrapper = new MFXIconWrapper(icono, 24, 32);
		MFXRectangleToggleNode toggleNode = new MFXRectangleToggleNode(texto, wrapper);
		toggleNode.setAlignment(Pos.CENTER_LEFT);
		toggleNode.setMaxWidth(Double.MAX_VALUE);
		toggleNode.setToggleGroup(toggleGroup);
		if (rotate != 0) wrapper.getIcon().setRotate(rotate);
		return toggleNode;
	}

	public void setStage(Stage stage){
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
}
