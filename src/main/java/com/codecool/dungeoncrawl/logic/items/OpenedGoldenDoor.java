package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class OpenedGoldenDoor extends Item {
    public OpenedGoldenDoor(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "opened-golden-door";
    }
}
