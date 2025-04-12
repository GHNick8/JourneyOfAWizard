package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;

public final class Player extends Entity {
    GamePanel gamePanel;
    KeyHandler keyHandler;
    public Player (GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }
    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/resources/player/walking sprites/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/resources/player/walking sprites/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/resources/player/walking sprites/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/resources/player/walking sprites/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/resources/player/walking sprites/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/resources/player/walking sprites/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/resources/player/walking sprites/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/resources/player/walking sprites/boy_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void update() {
        if (keyHandler.upPressed == true || keyHandler.downPressed == true || 
                keyHandler.leftPressed == true || keyHandler.rightPressed == true) {
            if (keyHandler.upPressed == true) {
                direction = "up";
                y -= speed;
            }
            else if (keyHandler.downPressed == true) {
                direction = "down";
                y += speed;
            }
            else if (keyHandler.leftPressed == true) {
                direction = "left";
                x -= speed;
            }
            else if (keyHandler.rightPressed == true) {
                direction = "right";
                x += speed;
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
        g2.drawImage(image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
        /* 
        Debug & Testing
        Rectangle sprite 
        g2.setColor(Color.white);
        g2.fillRect(x, y, gamePanel.tileSize, gamePanel.tileSize);
        */
    }
}