package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;


public class Teleporter extends Item {
    public Teleporter(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "teleporter";
    }
}
