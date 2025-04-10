package game.entities;

import game.graphics.Camera;
import java.awt.Graphics;
import java.awt.Rectangle;

/* Abstract class: is a restricted class that cannot be used to create objects 
(to access it, it must be inherited from another class). */
public abstract class Entity {
    protected int x, y, width, height;
    protected Camera camera;

    /* A constructor in Java is a special method that is used to initialize objects. 
    The constructor is called when an object of a class is created. It can be used to set initial values for object attributes */
    public Entity(int x, int y, int width, int height, Camera camera) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.camera = camera;
    }

    public abstract void update();
    public abstract void render(Graphics g);

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int getX() { return x; }
    public int getY() { return y; }
}
