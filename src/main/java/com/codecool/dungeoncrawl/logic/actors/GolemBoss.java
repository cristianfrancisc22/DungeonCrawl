package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class GolemBoss extends Actor {


    public GolemBoss(Cell cell) {
        super(cell);
        int strength = 5;
        setStrength(strength);
        int health = 20;
        setHealth(health);

    }

    @Override
    public String getTileName() {
        return "golem-boss";
    }
}