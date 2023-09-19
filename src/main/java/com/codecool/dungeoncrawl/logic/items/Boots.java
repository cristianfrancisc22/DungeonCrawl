package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class Boots extends Item{

    public Boots(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "boots";
    }
}

