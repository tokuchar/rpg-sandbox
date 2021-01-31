package com.oncors.rpg.api;

import com.oncors.rpg.Trace;
import com.oncors.rpg.dto.npc.Troll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@EnableSwagger2
@RequestMapping("/rpgGames")
public class RpgGameApi {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    HttpServletRequest request;

    @Trace
    @PostMapping
    public ResponseEntity<Void> play(@RequestHeader HttpHeaders httpHeaders) {
        log.info("play.. eh..");
        restTemplate.exchange("http://localhost:8081/npc-s",
                HttpMethod.GET,
                new HttpEntity<>(httpHeaders),
                Troll.class
        );
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
