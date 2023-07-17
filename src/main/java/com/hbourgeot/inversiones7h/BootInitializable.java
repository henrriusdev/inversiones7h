package com.hbourgeot.inversiones7h;

import org.springframework.context.ApplicationContextAware;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;

public interface BootInitializable extends Initializable, ApplicationContextAware {

  void initConstruct();

  void stage(Stage primaryStage);

  Node initView();

  void initValidator();
}
