package com.codecool.dungeoncrawl.logic;


import com.codecool.dungeoncrawl.logic.actors.*;

import com.codecool.dungeoncrawl.logic.items.*;
import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    static final String TXT_EXTENSION = ".txt";

    static final String SLASH = "/";
    public static GameMap loadMap(String mapName) {
        System.out.println(mapName);
        InputStream is = MapLoader.class.getResourceAsStream(SLASH + mapName + TXT_EXTENSION);
        assert is != null;
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY, mapName);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ' -> cell.setType(CellType.EMPTY);
                        case '#' -> cell.setType(CellType.WALL);
                        case '.' -> cell.setType(CellType.FLOOR);
                        case 's' -> {
                            cell.setType(CellType.FLOOR);
                            map.addSkeleton(new Skeleton(cell));
                        }
                        case '@' -> {
                            cell.setType(CellType.FLOOR);
//                            map.setPlayer(new Player(cell, "Player"));
                            map.setPlayer(new Player(cell));
                        }
                        case '*' -> {
                            cell.setType(CellType.FLOOR);
                            new Sword(cell);
                        }
                        case '5' -> {
                            cell.setType(CellType.FLOOR);
                            new Bow(cell);
                        }
                        case '6' -> {
                            cell.setType(CellType.FLOOR);
                            new Snake(cell);
                        }
                        case '7' -> {
                            cell.setType(CellType.FLOOR);
                            new ClosedGoldenDoor(cell);
                        }
                        case 'a' -> {
                            cell.setType(CellType.FLOOR);
                            map.setGhost(new Ghost(cell));
                        }
                        case '1' -> {
                            cell.setType(CellType.FLOOR);
                            new ChestPlate(cell);
                        }
                        case '2' -> {
                            cell.setType(CellType.FLOOR);
                            new Helmet(cell);
                        }
                        case '3' -> {
                            cell.setType(CellType.FLOOR);
                            new Shield(cell);
                        }
                        case '4' -> {
                            cell.setType(CellType.FLOOR);
                            new Boots(cell);
                        }
                        case 'g' -> {
                            cell.setType(CellType.FLOOR);
                            map.setGolemBoss(new GolemBoss(cell));
                        }
                        case 'l' -> {
                            cell.setType(CellType.FLOOR);
                            map.setLeprechaunBoss(new LeprechaunBoss(cell));
                        }
                        case 'm' -> {
                            cell.setType(CellType.FLOOR);
                            map.setDarkMageBoss(new DarkMageBoss(cell));
                        }
                        case 'i' -> {
                            cell.setType(CellType.FLOOR);
                            map.setTheIntangibleBoss(new TheIntangibleBoss(cell));
                        }
                        case 'k' -> {
                            cell.setType(CellType.FLOOR);
                            map.setFinalBoss(new TheUndyingKing(cell));
                        }
                        case '8' -> {
                            cell.setType(CellType.FLOOR);
                            new Scythe(cell);
                        }
                        case '9' -> {
                            cell.setType(CellType.FLOOR);
                            new Poleaxe(cell);
                        }
                        case 'x' -> {
                            cell.setType(CellType.FLOOR);
                            new Teleporter(cell);
                        }
                        case ';' -> {
                            cell.setType(CellType.TREE);
                        }
                        case ',' -> {
                            cell.setType(CellType.GRASS);
                        }
                        case '{' -> {
                            cell.setType(CellType.FLOOR);
                            new SapphireDoorClosed(cell);
                        }
                        case ']' -> {
                            cell.setType(CellType.FLOOR);
                            new CrimsonDoorClosed(cell);
                        }
                        case 't' -> {
                            cell.setType(CellType.FLOOR);
                            new Ladder(cell);
                        }
                        default -> throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

}
