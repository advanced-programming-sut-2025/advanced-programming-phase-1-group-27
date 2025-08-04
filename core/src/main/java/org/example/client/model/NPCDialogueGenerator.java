package org.example.client.model;

import org.example.common.utils.OllamaService;
import org.example.server.models.NPCs.NPC;

public class NPCDialogueGenerator implements Runnable {
    private final NPC npc;

    public NPCDialogueGenerator(NPC npc) {
        this.npc = npc;
    }

    @Override
    public void run() {
//        OllamaService.queryModelWithRole()
    }
}
