package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class CrimsonDoorClosed extends Item {
    public CrimsonDoorClosed(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "crimson-door-closed";
    }
}
