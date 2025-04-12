package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;

public final class Player extends Entity {
    GamePanel gamePanel;
    KeyHandler keyHandler;
    public final int screenX;
    public final int screenY;
    public Player (GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        screenX = gamePanel.screenWidth/2 - (gamePanel.tileSize/2);
        screenY = gamePanel.screenHeight/2 - (gamePanel.tileSize/2);
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;
        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues() {
        worldX = gamePanel.tileSize * 23;
        worldY = gamePanel.tileSize * 21;
        speed = 4;
        direction = "down";
    }
    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/resources/player/walking/walking_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/resources/player/walking/walking_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/resources/player/walking/walking_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/resources/player/walking/walking_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/resources/player/walking/walking_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/resources/player/walking/walking_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/resources/player/walking/walking_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/resources/player/walking/walking_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void update() {
        if (keyHandler.upPressed == true || keyHandler.downPressed == true || 
                keyHandler.leftPressed == true || keyHandler.rightPressed == true) {
            if (keyHandler.upPressed == true) {
                direction = "up";
            }
            else if (keyHandler.downPressed == true) {
                direction = "down";
            }
            else if (keyHandler.leftPressed == true) {
                direction = "left";
            }
            else if (keyHandler.rightPressed == true) {
                direction = "right";
            }
            collisionOn = false;
            gamePanel.cd.checkTile(this);
            if (collisionOn == false) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }
            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } 
                else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch(direction) {
            case "up" -> {
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
            }
            case "down" -> {
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
            }
            case "left" -> {
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
            }
            case "right" -> {
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
            }
            /*
            Debug & Testing 
            case "up" -> image = up1;
            case "down" -> image = down1;
            case "left" -> image = left1;
            case "right" -> image = right1;
            */
        }
        g2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
        /* 
        Debug & Testing
        Rectangle sprite 
        g2.setColor(Color.white);
        g2.fillRect(x, y, gamePanel.tileSize, gamePanel.tileSize);
        */
    }
}