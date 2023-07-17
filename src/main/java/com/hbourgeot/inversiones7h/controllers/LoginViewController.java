package com.hbourgeot.inversiones7h.controllers;

import com.hbourgeot.inversiones7h.BootInitializable;
import io.github.palexdev.materialfx.controls.*;
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

import com.hbourgeot.inversiones7h.MaterialJavaResourceLoader;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@FxmlView("LoginView.fxml")
public class LoginViewController implements BootInitializable {
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

	@Autowired
	private FxWeaver fxWeaver;

	private ApplicationContext applicationContext;

	public LoginViewController() {
		this.toggleGroup = new ToggleGroup();
		ToggleButtonsUtil.addAlwaysOneSelectedSupport(toggleGroup);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		this.closeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> Platform.exit());
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
		Image image = new Image(MaterialJavaResourceLoader.load("logo_alt.png"), 64, 64, true, true);
		ImageView logo = new ImageView(image);
		Circle clip = new Circle(30);
		clip.centerXProperty().set(logo.layoutBoundsProperty().get().getCenterX());
		clip.centerYProperty().set(logo.layoutBoundsProperty().get().getCenterY());
		logo.setClip(clip);
		logoContainer.getChildren().add(logo);
	}

	private void initializeLoader() {
		Parent loginController = fxWeaver.loadView(LoginController.class);
		Parent sobreController = fxWeaver.loadView(SobreController.class);

		ToggleButton loginBtn = createToggle("fas-circle-dot", "Inicio de sesion");
		ToggleButton sobreBtn = createToggle("fas-icons", "Sobre nosotros");

		loginBtn.setOnAction(event -> {
			contentPane.getChildren().setAll(loginController);
		});

		sobreBtn.setOnAction(event -> {
			contentPane.getChildren().setAll(sobreController);
		});

		navBar.getChildren().add(loginBtn);
		navBar.getChildren().add(sobreBtn);

		contentPane.getChildren().setAll(loginController);
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
