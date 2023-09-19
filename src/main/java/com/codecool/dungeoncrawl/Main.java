package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;
import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.items.CrimsonDoor;
import com.codecool.dungeoncrawl.logic.items.Item;
import com.codecool.dungeoncrawl.logic.items.OpenedGoldenDoor;
import com.codecool.dungeoncrawl.logic.items.SapphireDoor;
import com.codecool.dungeoncrawl.model.PlayerModel;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;


public class Main extends Application {
    Stage window;
    public static int VISIBLE_TILES_SIZE = 14;
    GameMap map = MapLoader.loadMap("game-map");
    Canvas canvas = new Canvas(
            VISIBLE_TILES_SIZE * Tiles.TILE_WIDTH,
            VISIBLE_TILES_SIZE * Tiles.TILE_WIDTH);
    Image icon;
    Media backgroundMedia;
    MediaPlayer backgroundMediaPlayer;

    GraphicsContext context = canvas.getGraphicsContext2D();
    Button pickUpButton = new Button("Pick up");
    Button importGame = new Button("Import Save");
    Button exportGame = new Button("Export Save");
    Label healthLabel = new Label();
    Label inventory = new Label();
    Label gameOptions = new Label("Game Options:");
    Label gameStats = new Label("Game Stats:");
    Label strength = new Label();
    GameDatabaseManager dbManager;
    Scene saveModalScene;
    Scene loadModalScene;
    ListView<String> listView = new ListView<>();

    Stage stage = new Stage();
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        setupDbManager();
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        GridPane saveModal = new GridPane();
        saveModal.setPadding(new Insets(10,10,10,10));
        saveModal.setVgap(8);
        saveModal.setHgap(10);

        Label nameLabel = new Label("Player Name:");
        GridPane.setConstraints(nameLabel, 0 ,0);

        TextArea nameInput = new TextArea();
        nameInput.setPromptText("-Your Name-");
        nameInput.setPrefSize(200,25);
        nameInput.setFocusTraversable(false);
        GridPane.setConstraints(nameInput, 0,1);
        Button saveButton = new Button("Save");
        GridPane.setConstraints(saveButton, 1,1);
        Button cancelButton = new Button("Cancel");
        GridPane.setConstraints(cancelButton, 1,2);
        saveModal.getChildren().addAll(nameLabel, nameInput, saveButton,cancelButton);
        saveButton.setOnAction(e -> {
            Player player = map.getPlayer();
            player.setName(nameInput.getText());
            dbManager.saveGameState(map);
            stage.close();
        });
        cancelButton.setOnAction(e -> stage.close());

        GridPane loadModal = new GridPane();
        loadModal.setPadding(new Insets(10,10,10,10));
        loadModal.setVgap(8);
        loadModal.setHgap(10);



//        ArrayList<String> results = new ArrayList<>();
        for(PlayerModel player: dbManager.getPlayers()){
            listView.getItems().add(player.getPlayerName());
        }
        Label loadTitle = new Label("Saves");
        handleItemClicks();
        loadModal.getChildren().addAll(loadTitle,listView);

        loadModalScene = new Scene(loadModal);
        saveModalScene = new Scene(saveModal);


        ui.add(pickUpButton, 0,0);
        pickUpButton.setFocusTraversable(false);

        gameOptions.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        ui.add(gameOptions, 0,1);

        ui.add(exportGame, 0, 3);
        exportGame.setFocusTraversable(false);

        ui.add(importGame, 0, 4);
        importGame.setFocusTraversable(false);

        gameStats.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        ui.add(gameStats, 0, 5);

        ui.add(new Label("Health: "), 0, 6);
        healthLabel.setFont(Font.font("Square", FontWeight.BOLD, FontPosture.REGULAR, 10));
        ui.add(healthLabel, 1, 6);

        ui.add(new Label("Strength:"), 0,7);
        strength.setFont(Font.font("Square", FontWeight.BOLD, FontPosture.REGULAR, 10));
        ui.add(strength, 1, 7);

        ui.add(new Label("Inventory:"), 0,8);
        ui.add(inventory, 0, 9);


        BorderPane borderPane = new BorderPane();

        icon = new Image("/images/game-icon.png");
        backgroundMedia = new Media(new File("src/main/resources/dummySongChoice.wav").toURI().toString());
        backgroundMediaPlayer = new MediaPlayer(backgroundMedia);
        backgroundMediaPlayer.setAutoPlay(true);

        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane, 800, 600);


        window = primaryStage;

        window.setScene(scene);

        refresh();

        scene.setOnKeyPressed(this::onKeyPressed);
        scene.setOnKeyReleased(this::onKeyReleased);
        window.getIcons().add(icon);
        window.setTitle("Dungeon Crawl");


        window.show();
    }



    private void onKeyReleased(KeyEvent keyEvent) {
        KeyCombination exitCombinationMac = new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN);
        KeyCombination exitCombinationWin = new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN);
        KeyCombination saveGameCombination = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_ANY);
        KeyCombination loadGameCombination = new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_ANY);
        if (exitCombinationMac.match(keyEvent)
                || exitCombinationWin.match(keyEvent)
                || keyEvent.getCode() == KeyCode.ESCAPE) {
            exit();
        } if (saveGameCombination.match(keyEvent)) {
//            stage.initOwner(window);
            stage.setTitle("Save Game");
            stage.setScene(saveModalScene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } else if (loadGameCombination.match(keyEvent)) {
//            stage.initOwner(window);
            stage.setTitle("Load Game");
            stage.setScene(loadModalScene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        }
    }

    public void showAndWaitScene() {
       stage.show();
    }


    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP -> {
                map.getPlayer().move(0, -1);
//                for (Skeleton skeleton : map.getSkeleton()) {
//                    skeleton.SkeletonMovement(ThreadLocalRandom.current().nextInt(-1, 1 + 1), ThreadLocalRandom.current().nextInt(-1, 1 + 1));
//                }
                refresh();
            }
            case DOWN -> {
                map.getPlayer().move(0, 1);

                refresh();
            }
            case LEFT -> {
                map.getPlayer().move(-1, 0);

                refresh();
            }
            case RIGHT -> {
                map.getPlayer().move(1, 0);

                refresh();
            }
//            case S -> {

//            }

        }
    }

    private void refresh() {
        Player player = map.getPlayer();
        int playerX = player.getX();
        int playerY = player.getY();
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < VISIBLE_TILES_SIZE; x++) {
            for (int y = 0; y < VISIBLE_TILES_SIZE; y++) {
                int middleX = playerX;
                int middleY = playerY;
                if (playerX - VISIBLE_TILES_SIZE / 2 < 0) {
                    middleX = VISIBLE_TILES_SIZE / 2;
                } else if (playerX + VISIBLE_TILES_SIZE / 2 > map.getWidth()) {
                    middleX = map.getWidth() - VISIBLE_TILES_SIZE / 2;
                }
                if (playerY - VISIBLE_TILES_SIZE / 2 < 0) {
                    middleY = VISIBLE_TILES_SIZE / 2;
                } else if (playerY + VISIBLE_TILES_SIZE / 2 > map.getHeight()) {
                    middleY = map.getHeight() - VISIBLE_TILES_SIZE / 2;
                }

                Cell cell = map.getCell(middleX - VISIBLE_TILES_SIZE / 2 + x, middleY - VISIBLE_TILES_SIZE / 2 + y);
                if (cell.getActor() != null & cell.getItem() != null) {
                    if (cell.getActor().getTileName().equals("player") & cell.getItem().getTileName().equals("teleporter")) {
                        map.getPlayer().removeItems();
                        map.getPlayer().setHealth(33);
                        player.setX(88);
                        player.setY(15);
                    } else if (cell.getActor().getTileName().equals("player") & cell.getItem().getTileName().equals("ladder")) {
                        player.setX(153);
                        player.setY(2);
                    }
                }
                if (cell.getActor() != null) {
                    if (cell.getActor().getHealth() > 0) {
                        Tiles.drawTile(context, cell.getActor(), x, y);
                    } else {
                        exit();
                    }
                } else if (cell.getItem() != null) {
                        for (Item item : map.getPlayer().getItems()) {
                            if (item != null) {
                                if (item.getTileName().equals("golden-key") & cell.getItem().getTileName().equals("closed-golden-door")) {
                                    Tiles.drawTile(context, new OpenedGoldenDoor(cell), x,y);
                                } else if (item.getTileName().equals("sapphire-key")  & cell.getItem().getTileName().equals("sapphire-door-closed")) {
                                    Tiles.drawTile(context, new SapphireDoor(cell), x, y);
                                } else if (item.getTileName().equals("crimson-key")  & cell.getItem().getTileName().equals("crimson-door-closed")) {
                                    Tiles.drawTile(context, new CrimsonDoor(cell), x, y);
                                }
                                Tiles.drawTile(context, cell.getItem(), x, y);
                            }

                        } Tiles.drawTile(context, cell.getItem(), x, y);
                }
                else {
                    Tiles.drawTile(context, cell, x, y);
                }
                if (cell.getActor() != null & cell.getItem() != null) {
                    pickUpButton.setOnAction(e -> {
                        if(cell.getActor() != null ) {
                            cell.getActor().addItem(cell.getItem());
                            cell.getActor().setStats(cell.getItem(), cell.getActor());
                            cell.removeItem();
                        }});
                }

            }
            healthLabel.setText("" + map.getPlayer().getHealth());
            strength.setText("" + map.getPlayer().getStrength());
            inventory.setText("" + map.getPlayer().getInventory());
        }
    }
    public void handleItemClicks() {
        listView.setOnMouseClicked(event -> {
            String selectedItem = listView.getSelectionModel().getSelectedItem();
            GameMap newMap = dbManager.loadGame(selectedItem);
            map.setCells(newMap.getCells());
            map.setPlayer(newMap.getPlayer());
            refresh();
        });
    }

    private void setupDbManager() {
        dbManager = new GameDatabaseManager();
        try {
            dbManager.setup();
        } catch (SQLException ex) {
            System.out.println("Cannot connect to database.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void exit() {
        try {
            stop();
        } catch (Exception e) {
            System.exit(1);
        }
        System.exit(0);
    }

}
