package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Scythe extends Item{

    public Scythe(Cell cell) {
        super(cell);

    }

    @Override
    public String getTileName() {
        return "scythe";
    }
}
