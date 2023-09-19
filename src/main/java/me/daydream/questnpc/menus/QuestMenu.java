package me.daydream.questnpc.menus;

import me.daydream.questnpc.QuestNPC;
import me.daydream.questnpc.managers.QuestManager;
import me.daydream.questnpc.models.KillQuest;
import me.daydream.questnpc.models.Quest;
import me.kodysimpson.simpapi.colors.ColorTranslator;
import me.kodysimpson.simpapi.menu.Menu;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class QuestMenu extends Menu {

    private final QuestManager questManager;

    public QuestMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
        this.questManager = QuestNPC.getPlugin().getQuestManager();
    }

    @Override
    public String getMenuName() {
        return "AlphaDuplos Quests";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        if (e.getCurrentItem().getType() == Material.DIAMOND_SWORD){
            String questName = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());

            for (Quest quest : questManager.getAvailableQuests()) {
                if (quest.getName().equalsIgnoreCase(questName)) {

                    Quest playersQuest = questManager.getQuest(p);
                    if (playersQuest != null) {
                        if (playersQuest.getName().equalsIgnoreCase(questName)) {
                            p.sendMessage(ColorTranslator.translateColorCodes("&cYou already doing this quest!"));
                        }else{
                            p.sendMessage(ColorTranslator.translateColorCodes("&cYou are already on a different quest!"));
                        }
                    }else{
                        questManager.giveQuest(p, quest);
                        p.sendMessage(ColorTranslator.translateColorCodes("&aYou have been given the quest &e\"" + quest.getName() + "\"&a!"));
                        p.sendMessage(ColorTranslator.translateColorCodes("&aTo complete this quest, you must &e" + quest.getDescription() + "&a!"));
                    }

                    p.closeInventory();
                    break;
                }
            }
        }

    }

    @Override
    public void setMenuItems() {

        questManager.getAvailableQuests().forEach(quest -> {
            ItemStack item;
            if (quest instanceof KillQuest killQuest){

                boolean isOnQuest = false;
                Quest playersQuest = questManager.getQuest(p);
                if (playersQuest != null) {
                    if (playersQuest.getName().equalsIgnoreCase(quest.getName())) {
                        isOnQuest = true;
                    }
                }

                item = makeItem(Material.DIAMOND_SWORD,
                        ColorTranslator.translateColorCodes("&6&l" + killQuest.getName()),
                        ColorTranslator.translateColorCodes("&5" + killQuest.getDescription()),
                        " ",
                        "&7Reward: &a$" + killQuest.getReward(),
                        " ",
                        (isOnQuest ? "&cYou are on this quest!" : "&aClick to accept!"));
                inventory.addItem(item);
            }
        });

    }
}