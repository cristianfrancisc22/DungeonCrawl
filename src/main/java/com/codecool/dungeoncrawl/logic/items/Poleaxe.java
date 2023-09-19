package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Poleaxe extends Item{
    public Poleaxe(Cell cell) {
        super(cell);

    }

    @Override
    public String getTileName() {
        return "poleaxe";
    }
}
