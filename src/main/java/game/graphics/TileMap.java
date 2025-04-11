package game.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import javax.imageio.ImageIO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class TileMap {
    private final int tileSize = 32;
    private final int width = 50;
    private final int height = 50;

    private final int[][] map;
    private final HashMap<Integer, Tile> tileRegistry = new HashMap<>();

    // Constructor TileMap - load tiles & load map from file setup 
    public TileMap() {
        map = new int[height][width];
        loadTiles();                       
        loadMapFromFile("map.txt"); 
    }

    private void loadTiles() {
        // Color-based tiles ; set true or false 
        tileRegistry.put(0, new Tile(0, Color.GREEN, true));
        tileRegistry.put(1, new Tile(1, Color.GRAY, false));
        tileRegistry.put(2, new Tile(2, Color.BLUE, true));
        tileRegistry.put(3, new Tile(3, Color.YELLOW, true));
        tileRegistry.put(4, new Tile(4, Color.DARK_GRAY, false));

        // Image-based tiles ; load tileset ; set true or false 
        try {
            BufferedImage tileset = ImageIO.read(getClass().getResourceAsStream("/sprites/tileset.png"));
            tileRegistry.put(5, new Tile(5, tileset.getSubimage(0, 0, tileSize, tileSize), true));
            tileRegistry.put(6, new Tile(6, tileset.getSubimage(32, 0, tileSize, tileSize), false)); 
            tileRegistry.put(7, new Tile(7, tileset.getSubimage(64, 0, tileSize, tileSize), false));
            tileRegistry.put(8, new Tile(8, tileset.getSubimage(96, 0, tileSize, tileSize), false));
        } catch (IOException e) {
            System.out.println("Tileset not found, skipping image tiles.");
        }
    }

    // Load the map layout from a file
    public void loadMapFromFile(String path) {
        try {
            InputStream in = getClass().getResourceAsStream("/maps/" + path);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
                for (int y = 0; y < height; y++) {
                    String line = reader.readLine();
                    if (line == null) break;
                    
                    String[] tokens = line.trim().split(" ");
                    for (int x = 0; x < tokens.length && x < width; x++) {
                        int id = Integer.parseInt(tokens[x]);
                        map[y][x] = id;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load map: " + path);
        }
    }

    public void render(Graphics g, Camera camera) {
        int startX = camera.getX() / tileSize;
        int startY = camera.getY() / tileSize;
        int endX = Math.min(startX + (800 / tileSize) + 1, width);
        int endY = Math.min(startY + (600 / tileSize) + 1, height);

        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                int id = map[y][x];
                Tile tile = tileRegistry.get(id);
                if (tile != null) {
                    int drawX = x * tileSize - camera.getX();
                    int drawY = y * tileSize - camera.getY();
                    tile.render(g, drawX, drawY, tileSize);
                }
            }
        }
    }

    public boolean isBlocked(int x, int y, int width, int height) {
        int startX = x / tileSize;
        int startY = y / tileSize;
        int endX = (x + width - 1) / tileSize;
        int endY = (y + height - 1) / tileSize;

        for (int row = startY; row <= endY; row++) {
            for (int col = startX; col <= endX; col++) {
                if (row >= 0 && row < height && col >= 0 && col < width) {
                    Tile tile = tileRegistry.get(map[row][col]);
                    if (tile != null && !tile.isWalkable()) return true;
                }
            }
        }
        return false;
    }
}
