package com.example.store;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
@org.springframework.kafka.annotation.EnableKafka
public class StoreServiceApplication {
  public static void main(String[] args){ SpringApplication.run(StoreServiceApplication.class, args); }
}
