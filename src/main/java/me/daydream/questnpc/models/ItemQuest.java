package me.daydream.questnpc.models;

import org.bukkit.Material;

public class ItemQuest extends Quest {

    private Material itemType;
    private int itemAmount;

    public ItemQuest(String name, String description, double reward, Material itemType, int itemAmount) {
        super(name, description, reward);
        this.itemType = itemType;
        this.itemAmount = itemAmount;
    }

    public Material getItemType() {
        return itemType;
    }

    public void setItemType(Material itemType) {
        this.itemType = itemType;
    }

    public int getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(int itemAmount) {
        this.itemAmount = itemAmount;
    }
}