package com.oncors.rpg.dto.npc;

import com.oncors.rpg.dto.base.Size;
import com.oncors.rpg.dto.base.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public abstract class Npc implements Serializable {
    private String name;
    private Size size;
    private State state;
}
