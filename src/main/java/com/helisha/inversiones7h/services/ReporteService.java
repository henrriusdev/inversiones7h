package com.helisha.inversiones7h.services;

import com.helisha.inversiones7h.MainApp;
import com.helisha.inversiones7h.entities.Producto;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Service
public class ReporteService {

  @Autowired
  private DataSource dataSource;

  public JasperPrint generarReporteVentas() throws Exception {
    try {
      Connection conexion = dataSource.getConnection();
      String path = Objects.requireNonNull(MainApp.class.getResource("ReporteVentas.jasper")).getPath();
      return JasperFillManager.fillReport(path, null, conexion);
    } catch (Exception ex){
      throw ex;
    }
  }

  public JasperPrint generarReporteCompras() throws Exception {
    try {
      Connection conexion = dataSource.getConnection();
      String path = Objects.requireNonNull(MainApp.class.getResource("ReporteCompras.jasper")).getPath();
      return JasperFillManager.fillReport(path, null, conexion);
    } catch (Exception ex){
      throw ex;
    }
  }

  public JasperPrint generarFactura(List<Producto> productos) throws Exception {
    try {
      String path = Objects.requireNonNull(MainApp.class.getResource("FacturaVenta.jasper").getPath());
      JRBeanCollectionDataSource datos = new JRBeanCollectionDataSource(productos);

      return JasperFillManager.fillReport(path, null, datos);
    } catch (Exception ex){
      throw ex;
    }
  }
}
