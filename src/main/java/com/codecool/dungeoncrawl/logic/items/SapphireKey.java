package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class SapphireKey extends Item{
    public SapphireKey(Cell cell) {
        super(cell);
    }
    @Override
    public String getTileName() {
        return "sapphire-key";
    }
}