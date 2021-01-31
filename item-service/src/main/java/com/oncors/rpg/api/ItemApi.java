package com.oncors.rpg.api;

import com.oncors.rpg.Trace;
import com.oncors.rpg.dto.item.Item;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Optional;

@RestController
@EnableSwagger2
@RequestMapping("/items")
public class ItemApi {
    @Trace
    @GetMapping
    //TODO: do wywalenia i zastąpienia normalną obsługą item-ków :)
    public ResponseEntity<Item> getTestItem(@RequestHeader HttpHeaders httpHeaders) {
        return ResponseEntity.of(Optional.of(null));
    }
}
