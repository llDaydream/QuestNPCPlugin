package me.daydream.questnpc.managers;

import me.daydream.questnpc.QuestNPC;
import me.daydream.questnpc.models.KillQuest;
import me.daydream.questnpc.models.Quest;
import me.kodysimpson.simpapi.colors.ColorTranslator;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestManager {

    private final HashMap<Player, Quest> quests;
    public QuestManager() {
        this.quests = new HashMap<>();
    }

    public List<Quest> getAvailableQuests() {
        List<Quest> availableQuests = new ArrayList<>();

        QuestNPC.getPlugin().getConfig().getConfigurationSection("quests.kill").getKeys(false).forEach(questName -> {

            String name = QuestNPC.getPlugin().getConfig().getString("quests.kill." + questName + ".name");
            String description = QuestNPC.getPlugin().getConfig().getString("quests.kill." + questName + ".description");
            double reward = QuestNPC.getPlugin().getConfig().getDouble("quests.kill." + questName + ".reward");
            String entityTypeName = QuestNPC.getPlugin().getConfig().getString("quests.kill." + questName + ".target.type");
            int count = QuestNPC.getPlugin().getConfig().getInt("quests.kill." + questName + ".target.count");

            EntityType entityType = EntityType.valueOf(entityTypeName);

            Quest quest = new KillQuest(name, description, reward, entityType, count);
            availableQuests.add(quest);
            System.out.println(quest);
        });

        return availableQuests;
    }

    public Quest getQuest(Player player) {
        return this.quests.get(player);
    }

    public void giveQuest(Player player, Quest quest){
        quest.setWhenStarted(System.currentTimeMillis());
        this.quests.put(player, quest);
    }

    public void completeQuest(Player p){

        Quest quest = this.quests.get(p);

        long timeTook = System.currentTimeMillis() - quest.getWhenStarted();
        String timeTookString = String.format("%02d:%02d", timeTook / 60000, (timeTook % 60000) / 1000);
        p.sendMessage(ColorTranslator.translateColorCodes("&aYou completed the quest &7\"" + quest.getName() + "\"&a in " + timeTookString));
        p.sendMessage("You received " + this.quests.get(p).getReward() + " coins!");

        this.quests.remove(p);
    }

}