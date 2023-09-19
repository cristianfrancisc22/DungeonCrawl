package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class CrimsonKey extends Item{
    public CrimsonKey(Cell cell) {
        super(cell);
    }
    @Override
    public String getTileName() {
        return "crimson-key";
    }
}
