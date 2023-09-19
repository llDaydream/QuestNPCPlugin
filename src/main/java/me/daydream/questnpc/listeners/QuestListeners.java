package me.daydream.questnpc.listeners;

import me.daydream.questnpc.QuestNPC;
import me.daydream.questnpc.managers.QuestManager;
import me.daydream.questnpc.models.KillQuest;
import me.daydream.questnpc.models.Quest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class QuestListeners implements Listener {

    @EventHandler
    public void onEntityKill(EntityDeathEvent event) {

        Player player = event.getEntity().getKiller();

        QuestManager questManager = QuestNPC.getPlugin().getQuestManager();
        Quest quest = questManager.getQuest(player);
        if (quest != null) {
            if (quest instanceof KillQuest killQuest) {
                if (killQuest.getTarget().equals(event.getEntity().getType())) {
                    killQuest.setProgress(killQuest.getProgress() + 1);
                    if (killQuest.getProgress() == killQuest.getAmount()) {
                        questManager.completeQuest(player);
                    }else{
                        player.sendMessage("You have killed " + killQuest.getProgress() + " " + killQuest.getTarget().name().toLowerCase() + "s.");
                        player.sendMessage("You need to kill " + (killQuest.getAmount() - killQuest.getProgress()) + " more " + killQuest.getTarget().name().toLowerCase() + "s.");
                    }
                }
            }
        }

    }

}