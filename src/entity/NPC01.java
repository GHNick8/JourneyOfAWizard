package entity;

import java.util.Random;
import main.GamePanel;

public final class NPC01 extends Entity {
    
    public NPC01(GamePanel gamePanel) {
        super(gamePanel);

        direction = "down";
        speed = 2;

        getImage();
    }

    public void getImage() {
        up1 = setup("/resources/npc/npc01/oldman_up_1");
        up2 = setup("/resources/npc/npc01/oldman_up_2");
        down1 = setup("/resources/npc/npc01/oldman_down_1");
        down2 = setup("/resources/npc/npc01/oldman_down_2");
        left1 = setup("/resources/npc/npc01/oldman_left_1");
        left2 = setup("/resources/npc/npc01/oldman_left_2");
        right1 = setup("/resources/npc/npc01/oldman_right_1");
        right2 = setup("/resources/npc/npc01/oldman_right_2");
    }

    @Override
    public void setAction() {

        actionLockCounter++;
        if (actionLockCounter == 120) {

            Random random = new Random();
            int i = random.nextInt(100)+1;

            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }

            actionLockCounter = 0;
        }
    }
}
