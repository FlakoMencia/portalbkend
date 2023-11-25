package com.personal.portalbkend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MainController {

    public MainController(){}

    /**
     * Unicamente para comprobar estado de API
     * @return
     */
    @GetMapping("/isAlive")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> areWeAlive() {
        String alive = "<h2> We stand Alive ( welcome to Mario Mencia - Colsubsidio Test ) <h2/>";
        return ResponseEntity.ok(alive);
    }


}
