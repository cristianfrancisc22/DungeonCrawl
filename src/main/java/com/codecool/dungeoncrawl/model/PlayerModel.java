package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.actors.Player;

public class PlayerModel extends BaseModel {
    private final int strength;
    private String playerName;
    private int hp;
    private int x;
    private int y;

    public PlayerModel(int strength, String playerName, int x, int y) {
        this.strength = strength;
        this.playerName = playerName;
        this.x = x;
        this.y = y;
    }

    public PlayerModel(Player player) {
        setPlayerName(player.getName());
        this.x = player.getX();
        this.y = player.getY();
        this.strength = player.getStrength();
        this.hp = player.getHealth();

    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getStrength() {
        return this.strength;
    }
}
