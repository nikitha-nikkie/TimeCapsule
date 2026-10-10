package com.timecapsule.backend;
import org.springframework.boot.SpringApplication;
 import org.springframework.boot.autoconfigure.SpringBootApplication;
 import org.springframework.scheduling.annotation.EnableScheduling;
 import org.springframework.web.bind.annotation.CrossOrigin;
@EnableScheduling @SpringBootApplication @CrossOrigin(origins = { "http://localhost:5173", "http://127.0.0.1:5173" }) public class BackendApplication {
public static void main(String[] args) {
    SpringApplication.run(BackendApplication.class, args);
}
}