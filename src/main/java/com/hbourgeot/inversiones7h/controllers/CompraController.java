package com.hbourgeot.inversiones7h.controllers;

import com.hbourgeot.inversiones7h.BootInitializable;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXSpinner;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.models.spinner.IntegerSpinnerModel;
import io.github.palexdev.materialfx.controls.models.spinner.SpinnerModel;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("compra.fxml")
public class CompraController implements BootInitializable {

  @FXML
  private MFXTextField productoField;

  @FXML
  private MFXTextField proveedorField;

  @FXML
  private MFXTextField idField;

  @FXML
  private MFXTextField montoField;

  @FXML
  private MFXButton registrarBtn;

  @FXML
  private MFXButton volverAtrasBtn;

  @FXML
  private MFXSpinner<Integer> cantidadSpinner;

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
  public void initialize(URL location, ResourceBundle resources) {
    SpinnerModel<Integer> spinnerModel = new IntegerSpinnerModel();
    spinnerModel.setValue(0);
    cantidadSpinner.setSpinnerModel(spinnerModel);
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

  }
}
