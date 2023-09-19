package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.PlayerInventoryModel;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IntentoryDaoJdbc implements InventoryDao{
    private final DataSource dataSource;
    private final PlayerDao playerDao;
    public IntentoryDaoJdbc(DataSource dataSource, PlayerDao playerDao) {
        this.dataSource = dataSource;
        this.playerDao = playerDao;
    }

    @Override
    public void add(PlayerInventoryModel item) {
        try (Connection conn = dataSource.getConnection()){
            String sql = "INSERT INTO player_inventory (item_name, player_id) VALUES(?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, item.getName());
            statement.setInt(2, item.getPlayer().getId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            item.setId(resultSet.getInt(1));
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(PlayerInventoryModel item) {
        try (Connection conn = dataSource.getConnection()){
            String sql ="UPDATE player_inventory SET player_id = ?, item_name = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, item.getPlayer().getId());
            statement.setString(2, item.getName());
            statement.setInt(3, item.getId());
            statement.executeUpdate();
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public PlayerInventoryModel get(int id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT player_id, item_name FROM player_inventory WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }

            PlayerModel playerModel = playerDao.get(resultSet.getInt(1));

            PlayerInventoryModel item = new PlayerInventoryModel(resultSet.getString(2), playerModel);
            item.setId(id);
            return item;
        } catch (SQLException e) {
            throw new RuntimeException("Error while reading player_items with id: " + id, e);
        }
    }

    @Override
    public List<PlayerInventoryModel> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, player_id, item_name FROM player_inventory";
            ResultSet resultSet = conn.createStatement().executeQuery(sql);

            List<PlayerInventoryModel> result = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                int player_id = resultSet.getInt(2);
                String item_name = resultSet.getString(3);
                PlayerModel playerModel = playerDao.get(player_id);

                PlayerInventoryModel item = new PlayerInventoryModel(item_name, playerModel);
                item.setId(id);
                result.add(item);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PlayerInventoryModel> getPlayerItems(int player_id) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, item_name FROM player_inventory WHERE player_id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, player_id);
            ResultSet resultSet = statement.executeQuery();

            List<PlayerInventoryModel> result = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String item_name = resultSet.getString(2);

                PlayerModel playerModel = playerDao.get(player_id);

                PlayerInventoryModel item = new PlayerInventoryModel(item_name, playerModel);
                item.setId(id);
                result.add(item);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
