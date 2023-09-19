package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.dao.GameStateDao;
import com.codecool.dungeoncrawl.dao.IntentoryDaoJdbc;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.items.*;

import java.util.Objects;


public class Player extends Actor {

    private String name;

    public Player(Cell cell) {
            super(cell);
            setStrength(5);
        }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTileName() {
        return "player";
    }

    public void loadUpItem(String name) {

        switch (name) {
            case "sword" -> inventory.add(new Sword(cell));
            case "boots" -> inventory.add(new Boots(cell));
            case "bow" -> inventory.add(new Bow(cell));
            case "chest-plate" -> inventory.add(new ChestPlate(cell));
            case "crimson-key" -> inventory.add(new CrimsonKey(cell));
            case "golden-key" -> inventory.add(new GoldenKey(cell));
            case "helmet" -> inventory.add(new Helmet(cell));
            case "poleaxe" -> inventory.add(new Poleaxe(cell));
            case "sapphire-key" -> inventory.add(new SapphireKey(cell));
            case "shield" -> inventory.add(new Shield(cell));
        }
    }

    public void setStats(String name, Actor actor) {
        switch (name) {
            case "sword" -> actor.setStrength(5);
            case "bow" -> actor.setStrength(10);
            case "chest-plate" -> actor.setHealth(10);
            case "helmet" -> actor.setHealth(5);
            case "boots" -> actor.setHealth(2);
            case "shield" -> actor.setHealth(8);
            case "scythe" -> actor.setStrength(15);
            case "poleaxe" -> actor.setStrength(25);
        }

        }
}
