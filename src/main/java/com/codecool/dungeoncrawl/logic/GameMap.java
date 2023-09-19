package com.codecool.dungeoncrawl.logic;


import com.codecool.dungeoncrawl.logic.actors.Ghost;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;

import java.util.ArrayList;
import java.util.List;

import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.Sword;


public class GameMap {
    private final int width;
    private final int height;
    private final Cell[][] cells;
    private Cell centerCell;

    private Player player;
    private Ghost ghost;
    private Skeleton skeleton;
    private GolemBoss golemBoss;
    private LeprechaunBoss leprechaunBoss;
    private DarkMageBoss darkMageBoss;
    private TheIntangibleBoss theIntangibleBoss;
    private TheUndyingKing theUndyingKing;

    private ArrayList<Skeleton> skeletons = new ArrayList<>();
    private final String fileName;


    public GameMap(int width, int height, CellType defaultCellType, String fileName) {
        this.width = width;
        this.height = height;
        this.fileName = fileName;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public void setPlayer(Player player) {
        this.player = player;
        repositionCenter();
    }

    public void repositionCenter() {
        int nextX;
        int nextY;
        if (player.getCell().getX() <= 10) {
            nextX = 10;
        } else nextX = Math.min(player.getCell().getX(), width - 11);

        if (player.getCell().getY() <= 10) {
            nextY = 10;
        } else nextY = Math.min(player.getCell().getY(), height - 11);

        centerCell = cells[nextX][nextY];

    }

    public void setGhost(Ghost ghost) {
        this.ghost = ghost;
    }
    public void addSkeleton(Skeleton skeleton) {
        skeletons.add(skeleton);
    }
    public void setGolemBoss(GolemBoss golemBoss) {
        this.golemBoss = golemBoss;
    }
    public void setLeprechaunBoss(LeprechaunBoss leprechaunBoss) {
        this.leprechaunBoss = leprechaunBoss;
    }
    public void setDarkMageBoss(DarkMageBoss darkMageBoss) {
        this.darkMageBoss = darkMageBoss;
    }
    public void setTheIntangibleBoss(TheIntangibleBoss theIntangibleBoss) {
        this.theIntangibleBoss = theIntangibleBoss;
    }
    public void setFinalBoss(TheUndyingKing theUndyingKing) {
        this.theUndyingKing = theUndyingKing;
    }


    public Player getPlayer() {
        return player;
    }

    public Ghost getGhost() {
        return ghost;
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public ArrayList<Skeleton> getSkeleton() {
        return skeletons;
    }

    public String getFileName() {
        return fileName;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public List<Item> getItems(){
        List<Item> items = new ArrayList<>();
        for(int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (cells[j][i].getItem() != null){
                    items.add(cells[j][i].getItem());
                }
            }
        }
        return items;
    }

    public List<Actor> getMonsters(){
        List<Actor> monsters = new ArrayList<>();
        for(int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (cells[j][i].getActor() != null && !cells[j][i].getActor().getTileName().equals("player")){
                    monsters.add(cells[j][i].getActor());
                }
            }
        }
        return monsters;
    }

    public void placeItem(Object obj){
        if(obj instanceof Actor){
            Actor actor = (Actor)obj;
            int x = actor.getX();
            int y = actor.getY();
            cells[x][y].setActor(actor);
        }
        else if (obj instanceof Item){
            Item item = (Item)obj;
            int x = item.getX();
            int y = item.getY();
            cells[x][y].setItem(item);
        }
    }

    public void setCells(Cell[][] cells){
        for (int x = 0; x < width; x++) {
            if (height >= 0) System.arraycopy(cells[x], 0, this.cells[x], 0, height);
        }
    }
}
