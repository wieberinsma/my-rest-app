package nl.han.resttest.domain.user.impl.controller;

import jdk.jfr.Registered;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public ResponseEntity<String> home() {
        return ResponseEntity.status(HttpStatus.OK).body("Hello world!");
    }
}
