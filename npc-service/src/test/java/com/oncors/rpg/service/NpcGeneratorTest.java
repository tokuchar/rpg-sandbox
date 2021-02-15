package com.oncors.rpg.service;

import com.oncors.rpg.model.Npc;
import com.oncors.rpg.repo.NpcRepo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NpcGeneratorTest {
    @MockBean
    NpcRepo npcRepo;

    @Autowired
    @InjectMocks
    NpcGenerator npcGenerator;

    @Test
    void generateNpc() {
        Npc randomNpc = npcGenerator.generateNpc();
        System.out.print(randomNpc);
    }
}
