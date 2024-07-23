package com.fincon.RenderController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("render/api")
public class RenderController {

    @GetMapping("get")
    public ResponseEntity<String> findAll() {
        return ResponseEntity.ok().body("Render API");
    }

}
