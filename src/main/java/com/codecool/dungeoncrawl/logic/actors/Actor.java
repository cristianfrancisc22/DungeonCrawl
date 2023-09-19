package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.items.CrimsonKey;
import com.codecool.dungeoncrawl.logic.items.GoldenKey;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.SapphireKey;

import java.util.ArrayList;


public abstract class Actor implements Drawable {
    Cell cell;
    private int health = 10;
    private int strength;
    public ArrayList<Item> inventory = new ArrayList<>();


    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);

        if (!(canHeroMove(nextCell))) {
            if (nextCell.getActor() != null) {
                    battle(cell, nextCell);
                    if (nextCell.getActor().getHealth() <= 0 & cell.getActor().getHealth() > 0) {
                        if (nextCell.getActor().getTileName().equals("golem-boss")) new SapphireKey(nextCell);
                        if (nextCell.getActor().getTileName().equals("leprechaun-boss")) new CrimsonKey(nextCell);
                        if (nextCell.getActor().getTileName().equals("snake")) new GoldenKey(nextCell);
                        if (nextCell.getActor().getTileName().equals("theintangible-boss")) new CrimsonKey(nextCell);
                        if (nextCell.getActor().getTileName().equals("final-boss")) new SapphireKey(nextCell);
                        cell.setActor(null);
                        nextCell.setActor(this);
                        cell = nextCell;
                }
            } else {
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
            }

        }
    }

    public void SkeletonMovement(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (!(canHeroMove(nextCell))) {
            if (nextCell.getActor() != null) {
                if (nextCell.getActor().getTileName().equals("player")) {
                    battle(nextCell, cell);
                    if (nextCell.getActor().getHealth() <= 0 & cell.getActor().getHealth() > 0) {
                        cell.setActor(null);
                        nextCell.setActor(this);
                        cell = nextCell;
                    }
                }
            } else {
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
            }
        }
    }


    private void battle(Cell playerCell, Cell monsterCell) {
        if (playerCell != null & monsterCell != null) {
            int monsterHealth = monsterCell.getActor().getHealth() - playerCell.getActor().getStrength();
            int playerHealth = playerCell.getActor().getHealth() - monsterCell.getActor().getStrength();
            monsterCell.getActor().setMobHealth(monsterHealth);
            if (monsterHealth > 0) playerCell.getActor().setMobHealth(playerHealth);
        }
    }

    private void setMobHealth(int health) {
        this.health = health;
    }


    boolean canHeroMove(Cell nextCell) {
//        if (cell.getActor() != null) {
////            if (cell.getActor().getTileName().equals("skeleton")) {
////                if (nextCell.getItem() != null) return nextCell.getItem().getTileName().equals("closed-door");
////                else if(nextCell.getActor() != null
////                        || nextCell.getItem() != null
////                        || nextCell.getTileName().equals("wall")) return true;
////                return false;
////
////            }
//        }
        if (nextCell.getItem() != null)
            return nextCell.getItem().getTileName().equals("closed-golden-door")
                || nextCell.getItem().getTileName().equals("crimson-door-closed")
                || nextCell.getItem().getTileName().equals("sapphire-door-closed") ;
        return nextCell.getTileName().equals("wall") ||  nextCell.getTileName().equals("tree");
    }
    public int getHealth() {
        return health;
    }
    public int getStrength(){
        return strength;
    }
    public void setHealth(int health) {
        this.health += health;
    }

    public void setStrength(int strength) {
        this.strength += strength;
    }

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public void setX(int x) {
        cell.setX(x);
    }

    public void setY(int y) {
        cell.setY(y);
    }

    public void addItem(Item item) {
        String item_name = item.getTileName();
        if (item_name.equals("sword") || item_name.equals("bow") || item_name.equals("chest-plate") ||
            item_name.equals("helmet") || item_name.equals("boots") || item_name.equals("shield") ||
            item_name.equals("scythe") || item_name.equals("poleaxe") || item_name.equals("sapphire-key") ||
            item_name.equals("crimson-key") || item_name.equals("golden-key")) inventory.add(item);
    }

    public void setStats(Item item, Actor actor) {
        if (item != null) {
            switch (item.getTileName()) {
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

    public StringBuilder getInventory(){
        StringBuilder items = new StringBuilder();
        for (Item item : inventory) {
            if (item != null) {
                items.append(item.getTileName());
                items.append("\n");
            }
        }
        return items;
    }
    public ArrayList<Item> getItems() {
        return inventory;
    }

    public void removeItems() {
        ArrayList<Item> oldInventory = inventory;
        this.inventory = new ArrayList<>();
        for (Item item: oldInventory) {
            if (item != null){
                if (!(item.getTileName().equals("sapphire-key"))
                        & !(item.getTileName().equals("crimson-key"))
                        & !(item.getTileName().equals("golden-key")))
                    inventory.add(item);
            }
        }
    }

}
