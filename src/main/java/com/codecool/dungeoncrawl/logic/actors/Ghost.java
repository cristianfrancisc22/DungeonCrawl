package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.util.Random;

public class Ghost extends Actor{
    Random random = new Random();

    public Ghost(Cell cell) {
        super(cell);
        move();
    }

    public void move() {
        try {
            Cell nextCell = cell.getNeighbor(0, random.nextInt(3) + 1);
            if (!cellNotValid(nextCell)) {
                cell.setActor(null);
                nextCell.setActor(this);
                cell = nextCell;
            }
        } catch (Exception e) {
                System.out.println("boom");
        }

        }

    private boolean cellNotValid(Cell cell) {
        return cell.getY() > 40 || cell.getX() > 20 || cell.getY() < 21 || cell.getX() < 12;
    }

    @Override
    public String getTileName() {
        return "ghost";
    }
}
