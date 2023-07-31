package com.helisha.inversiones7h.services;

import com.helisha.inversiones7h.MainApp;
import com.helisha.inversiones7h.entities.Producto;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service // servicio para reportes
public class ReporteService {

  @Autowired // origen de datos
  private DataSource dataSource;

  public JasperPrint generarReporteVentas() throws Exception { // funcion para generar reporte de ventas
    try {
      Connection conexion = dataSource.getConnection(); // obtenemos la conexion a la base de datos
      String path = Objects.requireNonNull(MainApp.class.getResource("ReporteVentas.jasper")).getPath();
      // obtenemos ruta al reporte

      return JasperFillManager.fillReport(path, null, conexion); // retornamos el reporte
    } catch (Exception ex) {
      throw ex;
    }
  }

  public JasperPrint generarReporteCompras() throws Exception {
    try {
      Connection conexion = dataSource.getConnection();
      String path = Objects.requireNonNull(MainApp.class.getResource("ReporteCompras.jasper")).getPath();
      return JasperFillManager.fillReport(path, null, conexion);
    } catch (Exception ex) {
      throw ex;
    }
  }

  public JasperPrint generarFactura(List<Producto> productos, BigDecimal montoTotal) throws Exception {
    String path = Objects.requireNonNull(MainApp.class.getResource("FacturaVenta.jasper").getPath());
    JRBeanCollectionDataSource datos = new JRBeanCollectionDataSource(productos);
    String url = "https://api.exchangedyn.com/markets/quotes/usdves/bcv";
    String tasaDolarBCV;
    try {
      URL obj = new URL(url);
      HttpURLConnection conexionHttp = (HttpURLConnection) obj.openConnection();
      conexionHttp.setRequestMethod("GET");
      BufferedReader in = new BufferedReader(new InputStreamReader(conexionHttp.getInputStream()));
      String inputLine;
      StringBuilder response = new StringBuilder();

      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }
      in.close();
      JSONObject json = new JSONObject(response.toString());
      JSONObject sources = json.getJSONObject("sources");
      JSONObject bcv = sources.getJSONObject("BCV");
      tasaDolarBCV = bcv.getString("quote");
      HashMap<String, Object> args = new HashMap<>();
      double doubleTasaBCV = Double.parseDouble(tasaDolarBCV);

      BigDecimal montoRedondeado = montoTotal.multiply(BigDecimal.valueOf(doubleTasaBCV)).setScale(2, RoundingMode.HALF_EVEN);
      args.put("MONTO_TOTAL", montoRedondeado.toString() + " Bs.");
      return JasperFillManager.fillReport(path, args, datos);
    } catch (Exception e) {
      throw e;
    }
  }
}
