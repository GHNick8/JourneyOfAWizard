package game.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.graphics.Camera;
import game.graphics.TileMap;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player extends Entity {
    // Instance variables 
    private int speed = 4;
    private int direction = 2;

    private TileMap map;
    // TODO: EntityManager 

    /* BufferedImage is a subclass of Image class. 
    It is used to handle and manipulate the image data. 
    A BufferedImage is made of ColorModel of image data. 
    All BufferedImage objects have an upper left corner coordinate of (0, 0). */
    private BufferedImage[][] walkingFrames;
    // private BufferedImage[][] attackingFrames;

    // Further initialising instance variables
    private int frameIndex = 0;
    private long lastFrameIndex = 0;
    private long frameDelay = 150;

    private long lastDamageTime = 0;
    private long damageCooldown = 1000;
    private long lastShotTime = 0;
    private long shotCooldown = 250;

    private int health = 10;
    private final int maxHealth = 12;

    // Constructor Player
    public Player(int x, int y, TileMap map, Camera camera) {
        super(x, y, 32, 36, camera);
        this.map = map;
        loadSprites(); 
    }

    public void damage(int amount) {
        long now = System.currentTimeMillis();
        if (now - lastDamageTime >= damageCooldown) {
            health -= amount;
            lastDamageTime = now;
            if (health < 0) health = 0;
        }
    }

    public void heal(int amount) {
        health += amount;
        if (health > maxHealth) health = maxHealth;
    }

    private void loadSprites() {
        try {
            BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream("/sprites/player.png"));
            // TODO: SpriteSheet

            // Sprite animation frame width & height
            int frameWidth = 32;
            int frameHeight = 36;

            // Sprite walking frames referring to image 
            walkingFrames = new BufferedImage[4][3];

            // For loop walking frames
            for (int row = 0; row < 4; row++) {
                // TODO: walking frames 
            }
            
        } catch (IOException | IllegalArgumentException e) {
            // Fallback graphic if player sprite doesn't load 
            walkingFrames = new BufferedImage[4][1];
            BufferedImage fallback = new BufferedImage(32, 36, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = fallback.createGraphics();
            g.setColor(Color.BLUE);
            g.fillRect(0, 0, 32, 36);
            g.dispose();

            // For loop walking frames
            for (int i = 0; i < 4; i++) {
                walkingFrames[i][0] = fallback; 
            }
        }
    }

    // Override abstract void update
    @Override
    public void update() {
        int newX = x;
        int newY = y;

        boolean moving = false;

        // TODO : Keyboard 


    }

    // Override abstract void render
    @Override
    public void render(Graphics g) {
        BufferedImage sprite;
        sprite = walkingFrames[direction][frameIndex];
        // TODO: Camera 
    }

    public boolean isNear(Entity other) {
        Rectangle playerRect = new Rectangle(x, y, width, height);
        Rectangle areaRect = new Rectangle(other.x -10, other.y - 10, other.width + 20, other.height + 20);
        return playerRect.intersects(areaRect);
    }
}


/* 

Delete later

if (Keyboard.keys[32] && !attacking) {
    attacking = true;
    attackStartTime = System.currentTimeMillis();
}

if (attacking) {
    if (System.currentTimeMillis() - attackStartTime > attackDuration) {
        attacking = false;
    }
}

 */