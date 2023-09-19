package me.daydream.questnpc.managers;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.protocol.game.ClientboundAddPlayerPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class NPCManager {

    private ServerPlayer alphaNPC;

    public void spawnAlpha(Player player){

        CraftPlayer craftPlayer = (CraftPlayer) player;
        MinecraftServer server = craftPlayer.getHandle().getServer();
        ServerLevel level = craftPlayer.getHandle().serverLevel().getLevel();

        ServerPlayer npc = new ServerPlayer(server, level, new GameProfile(UUID.randomUUID(), "AlphaDuplo"));
        npc.setPos(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());

        ServerGamePacketListenerImpl ps = craftPlayer.getHandle().connection;
        ps.send(new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, npc));
        ps.send(new ClientboundAddPlayerPacket(npc));

        player.sendMessage("AlphaDuplo has been spawned! Right mouse button to interact.");

        this.alphaNPC = npc;
    }

    public int getAlphaId(){
        return this.alphaNPC == null ? 0 : this.alphaNPC.getId();
    }

}