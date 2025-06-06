package object;

import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class OBJ_Boots extends SuperObject {

    GamePanel gamePanel;

    public OBJ_Boots(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        name = "Boots";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/resources/objects/boots.png"));
            utilityTool.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
