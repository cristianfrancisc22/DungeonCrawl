package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class SapphireDoorClosed extends Item {
    public SapphireDoorClosed(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "sapphire-door-closed";
    }
}
