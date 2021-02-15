package com.oncors.rpg.model;

import com.oncors.rpg.dto.base.Size;
import com.oncors.rpg.dto.base.State;
import io.lettuce.core.dynamic.annotation.Key;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.util.UUID;

@ToString
@SuperBuilder
@RedisHash("NPC")
@AllArgsConstructor
@NoArgsConstructor
public abstract class Npc implements Serializable {
    @Id
    String uuid;
    @Indexed
    @Getter
    private String name;
    @Getter
    private Size size;
    @Getter
    private State state;
    private Integer health;

    public Npc hit(Integer damage) {
        if (health - damage <= 0) {
            kill();
        } else {
            setUpAngryState();
            health = health - damage;
        }
        return this;
    }

    private void kill() {
        state = State.DEAD;
        health = 0;
    }

    private void setUpAngryState() {
        if (State.SLEEP == state) {
            state = State.ANGRY;
        }
    }
}
