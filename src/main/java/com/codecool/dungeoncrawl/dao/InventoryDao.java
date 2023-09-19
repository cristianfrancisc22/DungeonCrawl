package com.codecool.dungeoncrawl.dao;

import java.util.List;

import com.codecool.dungeoncrawl.model.PlayerInventoryModel;

public interface InventoryDao {

    void add(PlayerInventoryModel item);
    void update(PlayerInventoryModel item);
    PlayerInventoryModel get(int id);
    List<PlayerInventoryModel> getAll();
    List<PlayerInventoryModel> getPlayerItems(int player_id);
}
