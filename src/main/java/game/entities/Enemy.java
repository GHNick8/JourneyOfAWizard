// Reviewed & improved version

/*
    Optional Extensions
    Add enemy types (e.g., Slime, Shooter) by extending Enemy.

    Add animation frames or sprites.

    Add pathfinding later using the TileMap.
    
 */
package game.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import game.graphics.Camera;
import game.graphics.TileMap;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Enemy extends Entity {

    private int health = 4;
    private boolean alive = true;
    private int dx = 2, dy = 2;
    private int speed = 2;
    private Direction direction = Direction.RIGHT;
    private TileMap map;
    private Color tintColor = Color.RED;

    public enum Direction {
        UP, RIGHT, DOWN, LEFT
    }

    public Enemy(int x, int y, TileMap map, Camera camera) {
        super(x, y, 32, 36, camera);
        this.map = map;
    }

    public void setColor(Color c) {
        this.tintColor = c;
    }

    public void takeDamage(int amount) {
        health -= amount;
        if (health <= 0) {
            alive = false;
        }
    }

    @Override
    public void update() {
        if (!alive) return;

        // Simple bounce movement logic
        x += dx;
        y += dy;

        if (x <= 0 || x + width >= map.getWidth()) dx = -dx;
        if (y <= 0 || y + height >= map.getHeight()) dy = -dy;
    }

    @Override
    public void render(Graphics g) {
        if (!alive) return;
        g.setColor(tintColor);
        g.fillRect(x - camera.getX(), y - camera.getY(), width, height);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}


/*package game.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import game.graphics.TileMap;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Enemy extends Entity {
    // Instance variables 
    private int health = 4;
    private boolean alive = true;
    private int dx = 2, dy = 2;
    private int speed = 4;
    private int direction = 1;
    private TileMap map;

    // Default enemy color 
    private Color tintColor = Color.RED;

    public Enemy(int x, int y, TileMap map) {
        super(x, y, 32, 36, null);
        this.map = map;
    }

    public void setColor(Color c) {
        this.tintColor = c;
    }

    public void takeDamage(int amount) {
        health -= amount;
        if (health <= 0) {
            alive = false;
        }
    }

    public boolean isAlive() {
        return alive;
    }

    @Override
    public void update() {
        
    }

    // Override abstract void render
    @Override
    public void render(Graphics g) {
        
    }

    // Override Class Rectangle
    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
*/