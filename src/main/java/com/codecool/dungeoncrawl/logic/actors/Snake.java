package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Snake extends Actor{
    int strength = 25;
    int health = 25;
    public Snake(Cell cell) {
        super(cell);
        setStrength(strength);
        setHealth(health);
    }

    @Override
    public String getTileName() {
        return "snake";
    }
}
