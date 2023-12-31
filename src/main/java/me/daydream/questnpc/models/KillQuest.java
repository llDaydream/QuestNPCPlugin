package me.daydream.questnpc.models;

import org.bukkit.entity.EntityType;

public class KillQuest extends Quest {

    private EntityType target;
    private int amount;

    private int progress;

    public KillQuest(String name, String description, double reward, EntityType target, int amount) {
        super(name, description, reward);
        this.target = target;
        this.amount = amount;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public EntityType getTarget() {
        return target;
    }

    public void setTarget(EntityType target) {
        this.target = target;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}