package me.daydream.questnpc;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import me.daydream.questnpc.commands.QuestNPCCommand;
import me.daydream.questnpc.listeners.QuestListeners;
import me.daydream.questnpc.managers.NPCManager;
import me.daydream.questnpc.managers.QuestManager;
import me.daydream.questnpc.menus.QuestMenu;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.MenuManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class QuestNPC extends JavaPlugin {

    private static QuestNPC plugin;
    private NPCManager npcManager;
    private QuestManager questManager;

    @Override
    public void onEnable() {

        plugin = this;
        npcManager = new NPCManager();
        questManager = new QuestManager();

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        getCommand("questnpc").setExecutor(new QuestNPCCommand(npcManager));

        getServer().getPluginManager().registerEvents(new QuestListeners(), this);

        MenuManager.setup(getServer(), this);

        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        manager.addPacketListener(new PacketAdapter(this, PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent event) {

                PacketContainer packet = event.getPacket();
                int entityID = packet.getIntegers().read(0);

                if (npcManager.getAlphaId() == entityID){
                    EnumWrappers.Hand hand = packet.getEnumEntityUseActions().read(0).getHand();
                    EnumWrappers.EntityUseAction action = packet.getEnumEntityUseActions().read(0).getAction();

                    if (hand == EnumWrappers.Hand.MAIN_HAND && action == EnumWrappers.EntityUseAction.INTERACT) {
                        getServer().getScheduler().runTask(plugin, () -> {
                            try {
                                MenuManager.openMenu(QuestMenu.class, event.getPlayer());
                            } catch (MenuManagerException | MenuManagerNotSetupException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                }
            }
        });

    }

    @Override
    public void onDisable() {
        //disable logic here
    }

    public static QuestNPC getPlugin() {
        return plugin;
    }

    public NPCManager getNpcManager() {
        return npcManager;
    }

    public QuestManager getQuestManager() {
        return questManager;
    }
}