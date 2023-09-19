package com.codecool.dungeoncrawl.logic.items;

import com.codecool.dungeoncrawl.logic.Cell;

public class ChestPlate extends Item{

    public ChestPlate(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "chest-plate";
    }
}

