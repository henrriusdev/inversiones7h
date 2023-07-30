package com.helisha.inversiones7h.controllers;

import com.helisha.inversiones7h.BootInitializable;
import com.helisha.inversiones7h.entities.Compra;
import com.helisha.inversiones7h.services.CompraService;
import com.helisha.inversiones7h.services.ProveedorService;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.BigDecimalFilter;
import io.github.palexdev.materialfx.filter.LongFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

@Component
@FxmlView("VerCompras.fxml")
public class VerComprasController implements BootInitializable {

  @Autowired
  private FxWeaver fxWeaver;

  @Autowired
  private CompraService compraService;

  @Autowired
  private ProveedorService proveedorService;

  @FXML
  private MFXPaginatedTableView<Compra> tablaCompras;

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
    MFXTableColumn<Compra> columnaId = new MFXTableColumn<>("Código", false, Comparator.comparing(Compra::getId));
    MFXTableColumn<Compra> columnaProducto = new MFXTableColumn<>("Producto", false, Comparator.comparing(Compra::getProducto));
    MFXTableColumn<Compra> columnaProveedor = new MFXTableColumn<>("Proveedor", false, Comparator.comparing(Compra::getProveedor));
    MFXTableColumn<Compra> columnaCantidad = new MFXTableColumn<>("Cantidad comprada", false, Comparator.comparing(Compra::getCantidad));
    MFXTableColumn<Compra> columnaMonto = new MFXTableColumn<>("Monto de la compra", false, Comparator.comparing(Compra::getMontoTotal));

    columnaId.setRowCellFactory(producto -> new MFXTableRowCell<>(Compra::getId));
    columnaProducto.setRowCellFactory(producto -> new MFXTableRowCell<>(Compra::getProducto));
    columnaProveedor.setRowCellFactory(producto -> new MFXTableRowCell<>(Compra::getProveedor));
    columnaMonto.setRowCellFactory(producto -> new MFXTableRowCell<>(Compra::getCantidad));
    columnaCantidad.setRowCellFactory(producto -> new MFXTableRowCell<>(Compra::getMontoTotal));

    tablaCompras.getTableColumns().setAll(columnaId,columnaProducto,columnaProveedor,columnaCantidad, columnaMonto);
    tablaCompras.setRowsPerPage(15);
    tablaCompras.getFilters().setAll(
      new LongFilter<>("Código",Compra::getId),
      new StringFilter<>("Producto", Compra::getProducto),
      new StringFilter<>("Proveedor", Compra::getProveedor),
      new LongFilter<>("Cantidad comprada", Compra::getCantidad),
      new BigDecimalFilter<>("Monto de la compra", Compra::getMontoTotal)
    );
    recargarTabla();
  }

  public void recargarTabla(){
    tablaCompras.setItems(FXCollections.observableArrayList(compraService.findAll()));

    ObservableList<MFXTableColumn<Compra>> columnas = tablaCompras.getTableColumns();
    double anchoTabla = tablaCompras.getPrefWidth();

    for (var columna : columnas){
      columna.setMinWidth(anchoTabla / 5);
    }
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

  }
}
