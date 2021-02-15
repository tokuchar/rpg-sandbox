package com.oncors.rpg.api;

import com.oncors.rpg.Trace;
import com.oncors.rpg.dto.npc.Npc;
import com.oncors.rpg.dto.npc.Troll;
import com.oncors.rpg.service.NpcGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Optional;

@Slf4j
@RestController
@EnableSwagger2
@RequestMapping("/npc-s")
public class NpcApi {
    final RestTemplate restTemplate;
    final NpcGenerator npcGenerator;
    final ModelMapper modelMapper;

    public NpcApi(RestTemplate restTemplate, NpcGenerator npcGenerator, ModelMapper modelMapper) {
        this.restTemplate = restTemplate;
        this.npcGenerator = npcGenerator;
        this.modelMapper = modelMapper;
    }

    @Trace
    @GetMapping
    public ResponseEntity<Npc> getTestNpc() {
        com.oncors.rpg.model.Npc npc = npcGenerator.generateNpc();
        Troll dtoNpc = modelMapper.map(npc, Troll.class);
//        restTemplate.getForEntity("http://localhost:8080/items", Troll.class);
        log.info("npc: " + npc);
        return ResponseEntity.of(Optional.of(dtoNpc));
    }

    //FIXME: mapowaie abstract class
    // https://stackoverflow.com/questions/27170298/spring-reponsebody-requestbody-with-abstract-class
    @Trace
    @PostMapping
    public ResponseEntity<Npc> hitNpc(@RequestParam String name, @RequestParam Integer damage) {
        Troll npc = modelMapper.map(npcGenerator.hitNpc(name, damage), Troll.class);
        return ResponseEntity.of(Optional.of(npc));
    }
}
