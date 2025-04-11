package game.entities;

import java.awt.Color;
import java.awt.Graphics;

import game.graphics.Camera;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item extends Entity {
    // Instance variables items like coins & hearts
    private String type;
    private boolean collected = false;

    public Item(int x, int y, int width, int height, Camera camera, String type) {
        super(x, y, width, height, camera);
        this.type = type;
    }

    public boolean isCollected() {
        return collected;
    }

    public void collect() {
        collected = true;
    }

    @Override
    public void update() {}

    @Override
    public void render(Graphics g) {
        if (!collected) {
            switch (type) {
                case "coin" -> g.setColor(Color.YELLOW);
                case "heart" -> g.setColor(Color.PINK);
                default -> g.setColor(Color.WHITE);
            }
            g.fillOval( - camera.getX(), y - camera.getY(), width, height);
        }
    }
}
