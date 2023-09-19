package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class SapphireDoor extends Item {
    public SapphireDoor(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "sapphire-door";
    }
}
