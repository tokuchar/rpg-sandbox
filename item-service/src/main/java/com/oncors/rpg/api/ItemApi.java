package com.oncors.rpg.api;

import com.oncors.rpg.Trace;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@EnableSwagger2
@RequestMapping("/items")
public class ItemApi {
    @Trace
    @GetMapping
    //TODO: do wywalenia i zastąpienia normalną obsługą item-ków :)
    public ResponseEntity<Void> test() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
