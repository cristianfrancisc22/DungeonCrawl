package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.items.*;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.MapItemModel;
import com.codecool.dungeoncrawl.model.PlayerInventoryModel;
import com.codecool.dungeoncrawl.model.PlayerModel;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GameDatabaseManager {
    private static final String BLANK_EXTENSION = "_clean";
    static final String TXT_EXTENSION = ".txt";

    static final String SLASH = "/";
    private PlayerDao playerDao;
    private GameStateDao gameStateDao;
    private InventoryDao inventoryDao;
    private MapItemDao mapItemDao;

    public void setup() throws SQLException, IOException {
        DataSource dataSource = connect();
        playerDao = new PlayerDaoJdbc(dataSource);
        gameStateDao = new GameStateDaoJdbc(dataSource, playerDao);
        inventoryDao = new IntentoryDaoJdbc(dataSource, playerDao);
        mapItemDao = new MapItemDaoJdbc(dataSource, gameStateDao);
    }


    private DataSource connect() {
        PGSimpleDataSource dataSource;
        try {
            dataSource = new PGSimpleDataSource();
            String dbName = System.getenv("DB_NAME");
            String user = System.getenv("USER");
            String password = System.getenv("PASSWORD");

            dataSource.setDatabaseName(dbName);
            dataSource.setUser(user);
            dataSource.setPassword(password);

            System.out.println("Trying to connect");
            dataSource.getConnection().close();
            System.out.println("Connection ok.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dataSource;
    }
    public List<PlayerModel> getPlayers() {
        return playerDao.getAll();
    }


    public void saveGameState(GameMap map) {
        PlayerModel playerModel = new PlayerModel(map.getPlayer());
        String currentMap = map.getFileName();
        java.sql.Date saved_at = new java.sql.Date(System.currentTimeMillis());
        GameState gameState = new GameState(currentMap,saved_at,playerModel);
        playerDao.add(playerModel);
        gameStateDao.add(gameState);
        map.getPlayer().getItems().forEach((item) -> {
            String name = item.getTileName();
            PlayerInventoryModel playerItemModel = new PlayerInventoryModel(name, playerModel);
            inventoryDao.add(playerItemModel);;
        });
        map.getItems().forEach(item -> {
            String name = item.getTileName();
            int x = item.getX();
            int y = item.getY();
            MapItemModel mapItemModel = new MapItemModel(name, x, y, gameState);
            mapItemDao.add(mapItemModel);
        });
        map.getMonsters().forEach(monster -> {
            String name = monster.getTileName();
            int x = monster.getX();
            int y = monster.getY();
            MapItemModel mapItemModel = new MapItemModel(name, x, y, gameState);
            mapItemDao.add(mapItemModel);
        });

    }
    public GameMap loadGame(String playerName){
        PlayerModel playerModel = playerDao.get(playerName);
        GameState gameState = gameStateDao.getGameStateByPlayerId(playerModel.getId());
        List<MapItemModel> mapItems = mapItemDao.getMapItems(gameState.getId());
        List<PlayerInventoryModel> playerItems = inventoryDao.getPlayerItems(playerModel.getId());

        GameMap map = MapLoader.loadMap(gameState.getCurrentMap() + BLANK_EXTENSION);

        Cell playerCell = map.getCell(playerModel.getX(), playerModel.getY());
        Player player = new Player(playerCell);
        player.setName(playerModel.getPlayerName());
        player.setHealth(playerModel.getHp());

        playerItems.forEach(item -> player.loadUpItem(item.getName()));
        map.setPlayer(player);

        mapItems.forEach(item ->{
            Cell itemCell = map.getCell(item.getX(), item.getY());
            switch (item.getName()){
                case "sword":
                    map.placeItem(new Sword(itemCell));
                    break;
                case "scythe":
                    map.placeItem(new Scythe(itemCell));
                    break;
                case "boots":
                    map.placeItem(new Boots(itemCell));
                    break;
                case "bow":
                    map.placeItem(new Bow(itemCell));
                    break;
                case "chest-plate":
                    map.placeItem(new ChestPlate(itemCell));
                    break;
                case "crimson-key":
                    map.placeItem(new CrimsonKey(itemCell));
                    break;
                case "golden-key":
                    map.placeItem(new GoldenKey(itemCell));
                    break;
                case "helmet":
                    map.placeItem(new Helmet(itemCell));
                    break;
                case "poleaxe":
                    map.placeItem(new Poleaxe(itemCell));
                    break;
                case "sapphire-key":
                    map.placeItem(new SapphireKey(itemCell));
                    break;
                case "shield":
                    map.placeItem(new Shield(itemCell));
                    break;
                case "skeleton":
                    map.placeItem(new Skeleton(itemCell));
                    break;
                case "snake":
                    map.placeItem(new Snake(itemCell));
                    break;
                case "darkmage-boss":
                    map.placeItem(new DarkMageBoss(itemCell));
                    break;
                case "leprechaun-boss":
                    map.placeItem(new LeprechaunBoss(itemCell));
                    break;
                case "ghost":
                    map.placeItem(new Ghost(itemCell));
                    break;
                case "theintangible-boss":
                    map.placeItem(new TheIntangibleBoss(itemCell));
                    break;
                case "final-boss":
                    map.placeItem(new TheUndyingKing(itemCell));
                    break;
                case "opened-golden-door":
                    map.placeItem(new OpenedGoldenDoor(itemCell));
                    break;
                case "closed-golden-door":
                    map.placeItem(new ClosedGoldenDoor(itemCell));
                    break;
                case "crimson-door":
                    map.placeItem(new CrimsonDoor(itemCell));
                    break;
                case "crimson-door-closed":
                    map.placeItem(new CrimsonDoorClosed(itemCell));
                    break;
                case "sapphire-door":
                    map.placeItem(new SapphireDoor(itemCell));
                    break;
                case "sapphire-door-closed":
                    map.placeItem(new SapphireDoorClosed(itemCell));
                    break;
                case "teleporter":
                    map.placeItem(new Teleporter(itemCell));
                    break;
                case "golem-boss":
                    map.placeItem(new GolemBoss(itemCell));
                    break;
                case "ladder":
                    map.placeItem(new Ladder(itemCell));
            }
        });
        return map;
    }
}
