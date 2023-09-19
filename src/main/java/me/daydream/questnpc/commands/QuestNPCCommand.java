package me.daydream.questnpc.commands;

import me.daydream.questnpc.managers.NPCManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QuestNPCCommand implements CommandExecutor {

    private final NPCManager npcManager;

    public QuestNPCCommand(NPCManager npcManager) {
        this.npcManager = npcManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player player){
            npcManager.spawnAlpha(player);
        }

        return true;
    }
}