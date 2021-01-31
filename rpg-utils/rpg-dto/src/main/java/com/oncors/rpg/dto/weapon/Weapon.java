package com.oncors.rpg.dto.weapon;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public abstract class Weapon {
    private String name;
}
