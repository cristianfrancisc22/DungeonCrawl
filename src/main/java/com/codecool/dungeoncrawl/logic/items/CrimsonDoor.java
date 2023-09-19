package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class CrimsonDoor extends Item {
    public CrimsonDoor(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "crimson-door";
    }
}
