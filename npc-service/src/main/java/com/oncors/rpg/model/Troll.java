package com.oncors.rpg.model;

import com.oncors.rpg.dto.base.Size;
import com.oncors.rpg.dto.base.State;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.TypeAlias;

//@RedisHash(value = "TROLL", timeToLive = 3600)
@TypeAlias("TROLL")
@SuperBuilder
public class Troll extends Npc {
    public Troll() {
    }

    public Troll(String uuid, String name, Size size, State state, Integer health) {
        super(uuid, name, size, state, health);
    }
}
