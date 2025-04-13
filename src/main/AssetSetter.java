package main;

import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {
    GamePanel gamePanel;
    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    public void setObject() {
        gamePanel.obj[0] = new OBJ_Key();
        gamePanel.obj[0].worldX = 25 * gamePanel.tileSize;
        gamePanel.obj[0].worldY = 25 * gamePanel.tileSize;
        gamePanel.obj[1] = new OBJ_Door();
        gamePanel.obj[1].worldX = 19 * gamePanel.tileSize;
        gamePanel.obj[1].worldY = 27 * gamePanel.tileSize;
        gamePanel.obj[2] = new OBJ_Chest();
        gamePanel.obj[2].worldX = 17 * gamePanel.tileSize;
        gamePanel.obj[2].worldY = 29 * gamePanel.tileSize;
        gamePanel.obj[3] = new OBJ_Boots();
        gamePanel.obj[3].worldX = 15 * gamePanel.tileSize;
        gamePanel.obj[3].worldY = 23 * gamePanel.tileSize;
    }
}
