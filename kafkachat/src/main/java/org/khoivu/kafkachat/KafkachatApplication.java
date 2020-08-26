package org.khoivu.kafkachat;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class KafkachatApplication implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(KafkachatApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    log.info("Executing: command line runner");
    
  }
}
