package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import main.GamePanel;

public final class TileManager {
    GamePanel gamePanel;
    public Tile[] tile;
    public int mapTileNum[][];
    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tile = new Tile[10]; // Add tiles
        mapTileNum = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];
        getTileImage();
        loadMap("/resources/maps/world01.txt");
    }
    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/resources/tiles/simple_design/grass.png"));
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/resources/tiles/simple_design/earth.png"));
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/resources/tiles/simple_design/sand.png"));
            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/resources/tiles/simple_design/tree.png"));
            tile[3].collision = true;
            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/resources/tiles/simple_design/wall.png"));
            tile[4].collision = true;
            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/resources/tiles/simple_design/water.png"));
            tile[5].collision = true;
        } catch (IOException e) {
            System.out.println("Image png not working!");
            e.printStackTrace();
        }
    }
    public void loadMap(String filePath) {
        try {
            InputStream mapping = getClass().getResourceAsStream(filePath);
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(mapping))) {
                int col = 0;
                int row = 0;
                while(col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
                    String line = bufferedReader.readLine();
                    while(col < gamePanel.maxWorldCol) {
                        String numbers[] = line.split(" ");
                        int num = Integer.parseInt(numbers[col]);
                        mapTileNum[col][row] = num;
                        col++;
                    }
                    if (col == gamePanel.maxWorldCol) {
                        col = 0;
                        row++;
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;
        while(worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];
            int worldX = worldCol * gamePanel.tileSize;
            int worldY = worldRow * gamePanel.tileSize;
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
            // Improves rendering efficiency & (+- gamePanel.tileSize) prevents moving black border 
            if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX && 
                worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX && 
                worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY && 
                worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
            }
            worldCol++;
            if (worldCol == gamePanel.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
