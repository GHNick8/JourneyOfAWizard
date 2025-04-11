// Reviewed & improved version 
package game.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.entities.Projectile.Direction;
import game.graphics.Camera;
import game.graphics.SpriteSheet;
import game.graphics.TileMap;
import game.input.Keyboard;
import game.manager.EntityManager;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player extends Entity {

    private int speed = 4;
    private int direction = 2; // 0 = up, 1 = right, 2 = down, 3 = left

    private TileMap map;
    private EntityManager entityManager;

    /* BufferedImage is a subclass of Image class. 
    It is used to handle and manipulate the image data. 
    A BufferedImage is made of ColorModel of image data. 
    All BufferedImage objects have an upper left corner coordinate of (0, 0). 
    */
    private BufferedImage[][] walkingFrames;
    private int frameIndex = 0;
    private long lastFrameIndex = 0;
    private long frameDelay = 150;

    private long lastDamageTime = 0;
    private long damageCooldown = 1000;
    private long lastShotTime = 0;
    private long shotCooldown = 250;

    private int health = 10;
    private final int maxHealth = 12;

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
        health = Math.min(health + amount, maxHealth);
    }

    private void loadSprites() {
        try {
            BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream("/sprites/player.png"));
            SpriteSheet sheet = new SpriteSheet(spriteSheet);
            int frameWidth = 32, frameHeight = 36;

            walkingFrames = new BufferedImage[4][3];
            for (int row = 0; row < 4; row++) {
                for (int col = 0; col < 3; col++) {
                    walkingFrames[row][col] = sheet.getSprite(col * frameWidth, row * frameHeight, frameWidth, frameHeight);
                }
            }

        } catch (IOException | IllegalArgumentException e) {
            walkingFrames = new BufferedImage[4][1];
            BufferedImage fallback = new BufferedImage(32, 36, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = fallback.createGraphics();
            g.setColor(Color.BLUE);
            g.fillRect(0, 0, 32, 36);
            g.dispose();
            for (int i = 0; i < 4; i++) {
                walkingFrames[i][0] = fallback;
            }
        }
    }

    @Override
    public void update() {
        int newX = x, newY = y;
        boolean moving = false;

        if (Keyboard.keys[37]) { newX -= speed; direction = 3; moving = true; } // Left
        if (Keyboard.keys[39]) { newX += speed; direction = 1; moving = true; } // Right
        if (Keyboard.keys[38]) { newY -= speed; direction = 0; moving = true; } // Up
        if (Keyboard.keys[40]) { newY += speed; direction = 2; moving = true; } // Down

        if (!map.isBlocked(newX, newY, width, height)) {
            x = newX;
            y = newY;
        }

        if (Keyboard.keys[32]) { // Space to shoot
            long now = System.currentTimeMillis();
            if (now - lastShotTime >= shotCooldown) {
                lastShotTime = now;
                Projectile p = new Projectile(x + width / 2 - 4, y + height / 2 - 4, 8, 8, camera, getEnumDirection());
                if (entityManager != null) {
                    entityManager.addProjectile(p);
                }
            }
        }

        if (moving) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastFrameIndex > frameDelay) {
                frameIndex = (frameIndex + 1) % walkingFrames[direction].length;
                lastFrameIndex = currentTime;
            }
        } else {
            frameIndex = 0;
        }
    }

    @Override
    public void render(Graphics g) {
        BufferedImage sprite = walkingFrames[direction][frameIndex];
        g.drawImage(sprite, x - camera.getX(), y - camera.getY(), null);
    }

    public boolean isNear(Entity other) {
        Rectangle playerRect = new Rectangle(x, y, width, height);
        Rectangle areaRect = new Rectangle(other.x - 10, other.y - 10, other.width + 20, other.height + 20);
        return playerRect.intersects(areaRect);
    }

    private Direction getEnumDirection() {
        return switch (direction) {
            case 0 -> Direction.UP;
            case 1 -> Direction.RIGHT;
            case 2 -> Direction.DOWN;
            case 3 -> Direction.LEFT;
            default -> Direction.DOWN;
        };
    }
}


/*package game.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.graphics.Camera;
import game.graphics.SpriteSheet;
import game.graphics.TileMap;
import game.input.Keyboard;
import game.manager.EntityManager;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player extends Entity {
    // Instance variables 
    private int speed = 4;
    private int direction = 2;

    private TileMap map;
    private EntityManager entityManager;

    private BufferedImage[][] walkingFrames;

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
            SpriteSheet sheet = new SpriteSheet(spriteSheet);

            // Sprite animation frame width & height
            int frameWidth = 32;
            int frameHeight = 36;

            // Sprite walking frames referring to image 
            walkingFrames = new BufferedImage[4][3];

            // For loop walking frames
            for (int row = 0; row < 4; row++) {
                for (int col = 0; col < 3; col++) {
                    walkingFrames[row][col] = sheet.getSprite(col * frameWidth, row * frameHeight, frameWidth, frameHeight);
                }
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

        if (Keyboard.keys[37]) { newX -= speed; direction = 3; moving = true; } // Left
        if (Keyboard.keys[39]) { newX += speed; direction = 1; moving = true; } // Right
        if (Keyboard.keys[38]) { newY -= speed; direction = 0; moving = true; } // Up
        if (Keyboard.keys[40]) { newY += speed; direction = 2; moving = true; } // Down

        if (!map.isBlocked(newX, newY, width, height)) {
            x = newX;
            y = newY;
        }

        if (Keyboard.keys[32]) { // Spcae key pressed for projectile
            long now = System.currentTimeMillis();
            if (now - lastShotTime >= shotCooldown) {
                lastShotTime = now;
            }
        }

        if (moving) {
            long currentTime = System.currentTimeMillis();
            @SuppressWarnings("unused")
            long lastFrameTime;
            if (currentTime > frameDelay) {
                frameIndex = (frameIndex + 1) % walkingFrames[direction].length;
                lastFrameTime = currentTime;
            }
        } else {
            frameIndex = 0;
        }
    }

    // Override abstract void render
    @Override
    public void render(Graphics g) {
        BufferedImage sprite = walkingFrames[direction][frameIndex];
        g.drawImage(sprite, x - camera.getX(), y - camera.getY(), null);
    }

    public boolean isNear(Entity other) {
        Rectangle playerRect = new Rectangle(x, y, width, height);
        Rectangle areaRect = new Rectangle(other.x -10, other.y - 10, other.width + 20, other.height + 20);
        return playerRect.intersects(areaRect);
    }
}*/