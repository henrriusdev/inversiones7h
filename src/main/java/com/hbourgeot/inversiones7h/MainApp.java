
package com.hbourgeot.inversiones7h;

import com.hbourgeot.inversiones7h.controllers.LoginViewController;
import fr.brouillard.oss.cssfx.CSSFX;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class MainApp extends Application {
	private ConfigurableApplicationContext context;

	@Override
	public void init() throws Exception{
		String[] args = getParameters().getRaw().toArray(new String[0]);

		this.context = new SpringApplicationBuilder().sources(SpringApp.class).run(args);

		this.context
			.getAutowireCapableBeanFactory()
			.autowireBeanProperties(
				this,
				AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE,
				true
			);
	}
	@Override
	public void start(Stage stage) throws Exception {
		FxWeaver fxWeaver = context.getBean(FxWeaver.class);
		Parent root = fxWeaver.loadView(LoginViewController.class);
		CSSFX.start();
		Scene scene = new Scene(root);
		MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
		scene.setFill(Color.TRANSPARENT);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setTitle("Inversiones7H");
		stage.setScene(scene);
		stage.getIcons().add(new Image(MainApp.class.getResourceAsStream("logo.png")));
		stage.show();

		LoginViewController loginViewController = context.getBean(LoginViewController.class);
		loginViewController.setStage(stage);
	}

	@Override
	public void stop() throws Exception {
		context.close();
		Platform.exit();
	}
}

