package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static int TILE_WIDTH = 32;

    private static final Image tileset = new Image("/tiles.png", 543 * 2, 543 * 2, true, false);
    private static final Map<String, Tile> tileMap = new HashMap<>();
    public static class Tile {
        public final int x, y, w, h;
        Tile(int i, int j) {
            x = i * (TILE_WIDTH + 2);
            y = j * (TILE_WIDTH + 2);
            w = TILE_WIDTH;
            h = TILE_WIDTH;
        }
    }

    static {
        tileMap.put("empty", new Tile(0, 0));
        tileMap.put("wall", new Tile(10, 17));
        tileMap.put("floor", new Tile(2, 0));
        tileMap.put("player", new Tile(27, 0));
        tileMap.put("skeleton", new Tile(29, 6));
        tileMap.put("golden-key", new Tile(16, 23));
        tileMap.put("sapphire-key", new Tile(17, 23));
        tileMap.put("crimson-key", new Tile(18, 23));
        tileMap.put("sword", new Tile(0,30));
        tileMap.put("closed-golden-door", new Tile(10,10));
        tileMap.put("opened-golden-door", new Tile(11,10));
        tileMap.put("snake", new Tile(28,8));
        tileMap.put("bow", new Tile(8,28));
        tileMap.put("ghost", new Tile(26,6));
        tileMap.put("chest-plate", new Tile(4,23));
        tileMap.put("helmet", new Tile(4,22));
        tileMap.put("boots", new Tile(8,22));
        tileMap.put("shield", new Tile(7,26));
        tileMap.put("golem-boss", new Tile(31, 6));
        tileMap.put("leprechaun-boss", new Tile(26, 9));
        tileMap.put("darkmage-boss", new Tile(24, 1));
        tileMap.put("theintangible-boss", new Tile(24, 8));
        tileMap.put("final-boss", new Tile(28, 3));
        tileMap.put("scythe", new Tile(4, 24));
        tileMap.put("poleaxe", new Tile(7, 29));
        tileMap.put("teleporter", new Tile(21,24));
        tileMap.put("grass", new Tile(6,0));
        tileMap.put("tree", new Tile(3,1));
        tileMap.put("sapphire-door", new Tile(2,9));
        tileMap.put("sapphire-door-closed", new Tile(1,9));
        tileMap.put("crimson-door", new Tile(22,11));
        tileMap.put("crimson-door-closed", new Tile(13,18));
        tileMap.put("ladder", new Tile(21,1));
    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                x * TILE_WIDTH, y * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }
}
