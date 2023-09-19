package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class GoldenKey extends Item{
    public GoldenKey(Cell cell) {
        super(cell);
    }
    @Override
    public String getTileName() {
        return "golden-key";
    }
}
