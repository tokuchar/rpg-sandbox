package com.oncors.rpg.repo;

import com.oncors.rpg.model.Troll;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NpcRepo extends KeyValueRepository<Troll, String> {
    Optional<Troll> findByName(String name);
}
