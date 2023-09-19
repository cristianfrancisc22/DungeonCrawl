package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class ClosedGoldenDoor extends Item {
    public ClosedGoldenDoor(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "closed-golden-door";
    }
}
