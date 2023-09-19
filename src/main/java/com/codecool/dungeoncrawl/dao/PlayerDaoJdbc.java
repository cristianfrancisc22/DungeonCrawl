package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDaoJdbc implements PlayerDao {
    private DataSource dataSource;

    public PlayerDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(PlayerModel player) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO player (player_name, hp, x, y, strength) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, player.getPlayerName());
            statement.setInt(2, player.getHp());
            statement.setInt(3, player.getX());
            statement.setInt(4, player.getY());
            statement.setInt(5, player.getStrength());
            System.out.println(player.getX());
            System.out.println(player.getY());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            player.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(PlayerModel player) {

    }

    @Override
    public PlayerModel get(int id) {
        return null;
    }

    @Override
    public List<PlayerModel> getAll() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, player_name, x, y, hp, strength FROM player";
            ResultSet resultSet = conn.createStatement().executeQuery(sql);

            List<PlayerModel> result = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String player_name = resultSet.getString(2);
                int x = resultSet.getInt(4);
                int y = resultSet.getInt(5);
                int hp = resultSet.getInt(3);
                int strength = resultSet.getInt(6);

                PlayerModel playerModel = new PlayerModel(strength,player_name, x, y, hp);
                playerModel.setId(id);
                result.add(playerModel);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PlayerModel get(String name) {
        try(Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, hp, x, y, strength FROM player WHERE player_name = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            int id = resultSet.getInt(1);
            int hp = resultSet.getInt(2);
            int x = resultSet.getInt(3);
            int y = resultSet.getInt(4);
            int strength = resultSet.getInt(5);
            System.out.println(x);
            System.out.println(y);
            PlayerModel playerModel = new PlayerModel(strength,name, x, y, hp);
            playerModel.setId(id);
            return playerModel;
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}
