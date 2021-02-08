package com.oncors.rpg.api;

import com.oncors.rpg.Trace;
import com.oncors.rpg.dto.npc.Troll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j
@RestController
@EnableSwagger2
@RequestMapping("/rpgGames")
public class RpgGameApi {
    @Autowired
    RestTemplate restTemplate;

    @Trace
    @PostMapping
    public ResponseEntity<Void> play() {
        log.info("play.. eh..");
        restTemplate.getForEntity("http://localhost:8081/npc-s", Troll.class);
        restTemplate.getForEntity("http://localhost:8080/items", Troll.class);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
