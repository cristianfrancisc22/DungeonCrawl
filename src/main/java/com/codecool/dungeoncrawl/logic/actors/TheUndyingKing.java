package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class TheUndyingKing extends Actor {
    public TheUndyingKing(Cell cell) {
        super(cell);
        int strength = 20;
        setStrength(strength);
        int health = 45;
        setHealth(health);

    }

    @Override
    public String getTileName() {
        return "final-boss";
    }
}
