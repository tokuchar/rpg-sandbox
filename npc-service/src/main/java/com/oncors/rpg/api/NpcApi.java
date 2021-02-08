package com.oncors.rpg.api;

import com.oncors.rpg.Trace;
import com.oncors.rpg.dto.base.Size;
import com.oncors.rpg.dto.npc.Npc;
import com.oncors.rpg.dto.npc.Troll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Optional;

@Slf4j
@RestController
@EnableSwagger2
@RequestMapping("/npc-s")
public class NpcApi {
    @Autowired
    RestTemplate restTemplate;

    @Trace
    @GetMapping
    public ResponseEntity<Npc> getTestNpc() {
        Npc npc = Troll.builder()
                .name("Grrph")
                .size(Size.MEDIUM)
                .build();

        restTemplate.getForEntity("http://localhost:8080/items", Troll.class);
        log.info("npc: " + npc);
        return ResponseEntity.of(Optional.of(npc));
    }
}
