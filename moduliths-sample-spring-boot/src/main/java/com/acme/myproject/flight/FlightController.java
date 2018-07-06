package com.acme.myproject.flight;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlightController {

  @GetMapping("/flights/hello")
  public ResponseEntity<String> helloWorld() {
    return ResponseEntity.ok("Hello World");
  }

}
