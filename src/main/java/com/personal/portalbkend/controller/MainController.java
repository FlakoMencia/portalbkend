package com.personal.portalbkend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/isAlive")
public class MainController {

    public MainController(){

    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> areWeAlive() {
        String alive = "We stand Alive ( welcome to Mario Mencia - Colsubsidio Test )";
        return ResponseEntity.ok(alive);
    }


}
