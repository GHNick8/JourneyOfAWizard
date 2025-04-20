package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;

public final class TileManager {
    GamePanel gamePanel;
    public Tile[] tile;
    public int mapTileNum[][];
    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tile = new Tile[70]; // Add tiles
        mapTileNum = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];
        getTileImage();
        loadMap("/resources/maps/world01.txt");
    }
    public void getTileImage() { 
        // Placeholders to prevent null pointer exception 
        setup(0, "simple_design/grass", false);
        setup(1, "simple_design/grass", false);
        setup(2, "simple_design/grass", false);
        setup(3, "simple_design/grass", false);
        setup(4, "simple_design/grass", false);
        setup(5, "simple_design/grass", false);
        setup(6, "simple_design/grass", false);
        setup(7, "simple_design/grass", false);
        setup(8, "simple_design/grass", false);
        setup(9, "simple_design/grass", false);
        // Tiles used in game starts here 
        setup(10, "improved_design/grass00", false);
        setup(11, "improved_design/grass01", false);
        setup(12, "improved_design/earth", false);
        setup(13, "improved_design/floor01", false);
        setup(14, "improved_design/hut", false);
        setup(15, "improved_design/road00", false);
        setup(16, "improved_design/road01", false);
        setup(17, "improved_design/road02", false);
        setup(18, "improved_design/road03", false);
        setup(19, "improved_design/road04", false);
        setup(20, "improved_design/road05", false);
        setup(21, "improved_design/road06", false);
        setup(22, "improved_design/road07", false);
        setup(23, "improved_design/road08", false);
        setup(24, "improved_design/road09", false);
        setup(25, "improved_design/road10", false);
        setup(26, "improved_design/road11", false);
        setup(27, "improved_design/road12", false);
        setup(28, "improved_design/table01", true);
        setup(29, "improved_design/tree", true);
        setup(30, "improved_design/wall", true);
        setup(31, "improved_design/water00", true);
        setup(32, "improved_design/water01", true);
        setup(33, "improved_design/water02", true);
        setup(34, "improved_design/water03", true);
        setup(35, "improved_design/water04", true);
        setup(36, "improved_design/water05", true);
        setup(37, "improved_design/water06", true);
        setup(38, "improved_design/water07", true);
        setup(39, "improved_design/water08", true);
        setup(40, "improved_design/water09", true);
        setup(41, "improved_design/water10", true);
        setup(42, "improved_design/water11", true);
        setup(43, "improved_design/water12", true);
        setup(44, "improved_design/water13", true);
    }

    public void setup(int index, String imagePath, boolean collision) {
        UtilityTool utilityTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/resources/tiles/" + imagePath + ".png"));
            tile[index].image = utilityTool.scaleImage(tile[index].image, gamePanel.tileSize, gamePanel.tileSize);
            tile[index].collision = collision;

        } catch (IOException e) {
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

                    g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }
            worldCol++;
            if (worldCol == gamePanel.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
