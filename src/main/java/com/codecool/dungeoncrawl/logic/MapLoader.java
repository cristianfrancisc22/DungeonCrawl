package com.codecool.dungeoncrawl.logic;


import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;

import com.codecool.dungeoncrawl.logic.actors.Ghost;
import com.codecool.dungeoncrawl.logic.actors.Snake;
import com.codecool.dungeoncrawl.logic.items.*;
import com.codecool.dungeoncrawl.logic.objects.ClosedDoor;


import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap() {
        InputStream is = MapLoader.class.getResourceAsStream("/game-map.txt");
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            new Skeleton(cell);
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
//                            map.setPlayer(new Player(cell, "Player"));
                            map.setPlayer(new Player(cell));
                            break;
                        case '$':
                            cell.setType(CellType.FLOOR);
                            new Key(cell);
                            break;
                        case '*':
                            cell.setType(CellType.FLOOR);
                            new Sword(cell);
                            break;
                        case '5':
                            cell.setType(CellType.FLOOR);
                            new Bow(cell);
                            break;
                        case '6':
                            cell.setType(CellType.FLOOR);
                            new Snake(cell);
                            break;
                        case '7':
                            cell.setType(CellType.FLOOR);
                            new ClosedDoor(cell);
                            break;
                        case 'a':
                            cell.setType(CellType.FLOOR);
                            map.setGhost(new Ghost(cell));
                            break;
                        case '8':
                            cell.setType(CellType.FLOOR);
                            new chestPlate(cell);
                            break;
                        case '9':
                            cell.setType(CellType.FLOOR);
                            new Helmet(cell);
                            break;
                        case '}':
                            cell.setType(CellType.FLOOR);
                            new Shield(cell);
                            break;
                        case '%':
                            cell.setType(CellType.FLOOR);
                            new Boots(cell);
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

}
