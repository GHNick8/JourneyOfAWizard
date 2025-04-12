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
    Tile[] tile;
    int mapTileNum[][];
    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tile = new Tile[10]; // Add tiles
        mapTileNum = new int[gamePanel.maxScreenCol][gamePanel.maxScreenRow];
        getTileImage();
        loadMap("/resources/maps/map01.txt");
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
            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/resources/tiles/simple_design/wall.png"));
            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/resources/tiles/simple_design/water.png"));
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
                while(col < gamePanel.maxScreenCol && row < gamePanel.maxScreenRow) {
                    String line = bufferedReader.readLine();
                    while(col < gamePanel.maxScreenCol) {
                        String numbers[] = line.split(" ");
                        int num = Integer.parseInt(numbers[col]);
                        mapTileNum[col][row] = num;
                        col++;
                    }
                    if (col == gamePanel.maxScreenCol) {
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
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;
        while(col < gamePanel.maxScreenCol && row < gamePanel.maxScreenRow) {
            int tileNum = mapTileNum[col][row];
            g2.drawImage(tile[tileNum].image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
            col++;
            x += gamePanel.tileSize;
            if (col == gamePanel.maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += gamePanel.tileSize;
            }
        }
    }
}
