// Reviewed & improved version
package game.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import game.graphics.Camera;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Projectile extends Entity {

    public enum Direction {
        UP, RIGHT, DOWN, LEFT
    }

    private int speed = 10;
    private Direction direction;
    private int damage = 2;
    private int range = 300;
    private int distanceTraveled = 0;
    private boolean active = true;

    public Projectile(int x, int y, int width, int height, Camera camera, Direction direction) {
        super(x, y, width, height, camera);
        this.direction = direction;
    }

    @Override
    public void update() {
        if (!active) return;

        int dx = 0, dy = 0;
        switch (direction) {
            case UP -> dy = -speed;
            case RIGHT -> dx = speed;
            case DOWN -> dy = speed;
            case LEFT -> dx = -speed;
        }

        x += dx;
        y += dy;
        distanceTraveled += Math.abs(dx) + Math.abs(dy);

        if (distanceTraveled >= range) {
            deactivate();
        }

        // Future collision/offscreen logic
        // if (collidesWithEnemy() || isOffScreen()) {
        //     deactivate();
        // }
    }

    @Override
    public void render(Graphics g) {
        if (!active) return;
        g.setColor(Color.ORANGE);
        g.fillOval(x - camera.getX(), y - camera.getY(), width, height);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void deactivate() {
        active = false;
    }

    // Optional: reset for reuse
    public void reset(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.distanceTraveled = 0;
        this.active = true;
    }
}


/*package game.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import game.graphics.Camera;
import lombok.Getter;
import lombok.Setter;

// mvn clean compile to integrate lombok 

@Getter
@Setter
public class Projectile extends Entity {
    // Instance variables are variables that belong to an instance of a class
    private int speed = 10;
    private int direction;
    private int damage = 2;
    private int range = 300;
    private int distanceTraveled = 0;
    private boolean active = true;

    // Constructor Projectile
    public Projectile(int x, int y, int width, int height, Camera camera, int direction) {
        // super is used to refer to the immediate parent class object
        super(x, y, width, height, camera);
        this.direction = direction;
    }

    // Override abstract void update
    @Override
    public void update() {
        if (!active) return;

        int dx = 0, dy = 0;
        switch (direction) {
            case 0 -> dy = -speed;
            case 1 -> dx = speed;
            case 2 -> dy = speed;
            case 3 -> dx = -speed;
        }

        x += dx;
        y += dy;

        // absolute value 
        distanceTraveled += Math.abs(dx) + Math.abs(dy);

        if (distanceTraveled >= range) {
            active = false;
        }
    }

    // Override abstract void render
    @Override
    public void render(Graphics g) {
        if (!active) return;
        g.setColor(Color.ORANGE);
        g.fillOval(x - camera.getX(),  - camera.getY(), width, height);
    }

    // Override Class Rectangle
    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public boolean isActive() {
        return active;
    }

    public void deacitvate() {
        active = false;
    }
}
*/