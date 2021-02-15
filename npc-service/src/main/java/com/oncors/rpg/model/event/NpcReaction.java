package com.oncors.rpg.model.event;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NpcReaction {
    private String npc;
    private String description;
}
