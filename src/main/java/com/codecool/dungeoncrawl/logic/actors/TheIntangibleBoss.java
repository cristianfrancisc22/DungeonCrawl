package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class TheIntangibleBoss extends Actor {
    public TheIntangibleBoss(Cell cell) {
        super(cell);
        int strength = 20;
        setStrength(strength);
        int health = 45;
        setHealth(health);

    }

    @Override
    public String getTileName() {
        return "theintangible-boss";
    }
}