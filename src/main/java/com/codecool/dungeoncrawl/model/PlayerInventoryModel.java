package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.items.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Getter
@Setter
public class PlayerInventoryModel extends BaseModel{
    private String name;
    private PlayerModel player;
    public PlayerInventoryModel(String name, PlayerModel player) {
        this.name = name;
        this.player = player;
    }
}
