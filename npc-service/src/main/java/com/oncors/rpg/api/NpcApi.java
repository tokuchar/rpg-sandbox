package com.oncors.rpg.api;

import com.oncors.rpg.Trace;
import com.oncors.rpg.dto.base.Size;
import com.oncors.rpg.dto.npc.Npc;
import com.oncors.rpg.dto.npc.Troll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
@RestController
@EnableSwagger2
@RequestMapping("/npc-s")
public class NpcApi {
    @Autowired
    HttpServletRequest request;

    //FIXME: jak się pozbyć tego @Trace żeby prawidłowo doklejać spany? - request ma "uber-trace-id"
    @Trace
    @GetMapping
    public ResponseEntity<Npc> getTestItem(@RequestHeader HttpHeaders httpHeaders) {
        Npc npc = Troll.builder()
                .name("Grrph")
                .size(Size.MEDIUM)
                .build();

        log.info("npc: " + npc);
        return ResponseEntity.of(Optional.of(npc));
    }
}
