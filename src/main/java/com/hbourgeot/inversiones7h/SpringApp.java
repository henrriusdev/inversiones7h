package com.hbourgeot.inversiones7h;

import javafx.application.Application;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.spring.SpringFxWeaver;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringApp {
  public static void main(String[] args) {
    Application.launch(MainApp.class, args);
  }

  @Bean
  public FxWeaver fxWeaver(ConfigurableApplicationContext applicationContext) {
    // Would also work with javafx-weaver-core only:
    // return new FxWeaver(applicationContext::getBean, applicationContext::close);
    return new SpringFxWeaver(applicationContext);
  }
}
