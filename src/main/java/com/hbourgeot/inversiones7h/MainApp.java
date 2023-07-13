/*
 * Copyright (C) 2022 Parisi Alessandro
 * This file is part of MaterialFX (https://github.com/palexdev/MaterialFX).
 *
 * MaterialFX is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MaterialFX is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with MaterialFX.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.hbourgeot.inversiones7h;

import com.hbourgeot.inversiones7h.controllers.LoginController;
import fr.brouillard.oss.cssfx.CSSFX;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import com.hbourgeot.inversiones7h.controllers.CajaController;
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

		LoginController mainController = context.getBean(LoginController.class);
		mainController.setStage(primaryStage);
	}

	@Override
	public void stop() throws Exception{
		context.close();
	}
}

