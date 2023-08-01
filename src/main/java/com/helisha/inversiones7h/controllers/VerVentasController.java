package com.helisha.inversiones7h.controllers;

import com.helisha.inversiones7h.BootInitializable;
import com.helisha.inversiones7h.MainApp;
import com.helisha.inversiones7h.entities.Venta;
import com.helisha.inversiones7h.services.ReporteService;
import com.helisha.inversiones7h.services.VentaService;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.BigDecimalFilter;
import io.github.palexdev.materialfx.filter.LongFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.ResourceBundle;

@Component
@FxmlView("VerVentas.fxml")
public class VerVentasController implements BootInitializable {

  @Autowired
  private FxWeaver fxWeaver;

  @Autowired
  private VentaService ventaService;

  @Autowired
  private ReporteService reporteService;

  @FXML
  private MFXPaginatedTableView<Venta> tablaVentas;

  @FXML
  private MFXButton generarReporte;

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
    MFXTableColumn<Venta> columnaId = new MFXTableColumn<>("Código", false, Comparator.comparing(Venta::getId));
    MFXTableColumn<Venta> columnaCliente = new MFXTableColumn<>("Cliente", false, Comparator.comparing(Venta::getCliente));
    MFXTableColumn<Venta> columnaProducto = new MFXTableColumn<>("Productos", false, Comparator.comparing(Venta::getProductos));
    MFXTableColumn<Venta> columnaMonto = new MFXTableColumn<>("Monto de la venta", false, Comparator.comparing(Venta::getMontoTotal));

    columnaId.setRowCellFactory(producto -> new MFXTableRowCell<>(Venta::getId));
    columnaProducto.setRowCellFactory(producto -> new MFXTableRowCell<>(Venta::getProductos));
    columnaCliente.setRowCellFactory(producto -> new MFXTableRowCell<>(Venta::getCliente));
    columnaMonto.setRowCellFactory(producto -> new MFXTableRowCell<>(Venta::getMontoTotal));

    tablaVentas.getTableColumns().setAll(columnaId,columnaProducto,columnaCliente, columnaMonto);
    tablaVentas.setRowsPerPage(10);
    tablaVentas.getFilters().setAll(
      new LongFilter<>("Código",Venta::getId),
      new StringFilter<>("Cliente", Venta::getCliente),
      new StringFilter<>("Productos", Venta::getProductos),
      new BigDecimalFilter<>("Monto de la venta", Venta::getMontoTotal)
    );

    ObservableList<MFXTableColumn<Venta>> columnas = tablaVentas.getTableColumns();
    double anchoTabla = tablaVentas.getPrefWidth();

    for (var columna : columnas){
      columna.setMinWidth(anchoTabla / 4);
    }

    recargarTabla();
  }

  public void recargarTabla(){
    tablaVentas.setItems(FXCollections.observableArrayList(ventaService.findAll()));
  }

  public void generar() throws Exception {
    try {
      JasperPrint jasperPrint = reporteService.generarReporteVentas();

      String pdfPath = System.getProperty("user.dir") + "\\inversiones-data\\reporte_ventas.pdf"; // path where you want to save your pdf...
      JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPath);
      Runtime.getRuntime().exec("cmd /c start chrome file:///"+pdfPath);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

  }
}
