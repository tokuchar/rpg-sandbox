package com.oncors.rpg.service;

import com.github.javafaker.Faker;
import com.oncors.rpg.dto.base.Size;
import com.oncors.rpg.dto.base.State;
import com.oncors.rpg.model.Npc;
import com.oncors.rpg.model.Troll;
import com.oncors.rpg.repo.NpcRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class NpcGenerator {
    final Faker nameGenerator;
    final Random random;
    final NpcRepo repo;

    public NpcGenerator(Faker nameGenerator, Random random, NpcRepo repo) {
        this.nameGenerator = nameGenerator;
        this.random = random;
        this.repo = repo;
    }

    public Npc generateNpc() {
        Troll npc = Troll.builder()
                .uuid(UUID.randomUUID().toString())
                .name(nameGenerator.name().firstName())
                .health(random.nextInt(100))
                .size(Size.values()[random.nextInt(Size.values().length)])
                .state(State.values()[random.nextInt(State.values().length)]).build();

        repo.save(npc);
        return npc;
    }

    public Npc hitNpc(String name, Integer damage) {
        Optional<Troll> npc = repo.findByName(name);
        npc.ifPresent(n -> {
            n.hit(damage);
            deleteOnDeadOrSave(n);
        });

        return npc.get();
    }

    private void deleteOnDeadOrSave(Troll n) {
        if (State.DEAD == n.getState()) {
            repo.delete(n);
        } else {
            repo.save(n);
        }
    }
}
