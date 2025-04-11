package game.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tile {
    private final int id;
    private final Color color;
    private final boolean walkable;
    private final BufferedImage sprite;

    // Tile-size 
    public static final int SIZE = 32;

    // Color-based constructor
    public Tile(int id, Color color, boolean walkable) {
        this.id = id;
        this.color = color;
        this.walkable = walkable;
        this.sprite = null;
    }

    // Image-based constructor
    public Tile(int id, BufferedImage sprite, boolean walkable) {
        this.id = id;
        this.sprite = sprite;
        this.walkable = walkable;
        this.color = Color.MAGENTA; // fallback tint if needed
    }

    public void render(Graphics g, int x, int y, int size) {
        if (sprite != null) {
            g.drawImage(sprite, x, y, size, size, null);
        } else {
            g.setColor(color);
            g.fillRect(x, y, size, size);
        }
    }

    public boolean isWalkable() {
        return walkable;
    }
}
