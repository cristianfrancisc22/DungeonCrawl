package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Bow extends Item{

    public Bow(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "bow";
    }
}
