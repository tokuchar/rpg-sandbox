package com.oncors.rpg.dto.npc;

import com.oncors.rpg.dto.base.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Npc {
    private String name;
    private Size size;
}
