
package com.hbourgeot.inversiones7h;

import com.hbourgeot.inversiones7h.controllers.LoginViewController;
import fr.brouillard.oss.cssfx.CSSFX;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MainApp extends Application {
	private ConfigurableApplicationContext context;

	@Override
	public void init() throws Exception{
		SpringApplicationBuilder builder = new SpringApplicationBuilder(MainApp.class);
		context = builder.run(getParameters().getRaw().toArray(new String[0]));
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		CSSFX.start();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/LoginView.fxml"));
		loader.setControllerFactory(context::getBean);
		Parent root = loader.load();
		Scene scene = new Scene(root);
		MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
		scene.setFill(Color.TRANSPARENT);
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.setScene(scene);
		primaryStage.setTitle("MaterialFX Demo");
		primaryStage.show();

		LoginViewController mainController = context.getBean(LoginViewController.class);
		mainController.setStage(primaryStage);
	}

	@Override
	public void stop() throws Exception{
		context.close();
	}

	public static void main(String[] args) {
		launch(args);
	}
}

