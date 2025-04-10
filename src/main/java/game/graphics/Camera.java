package game.graphics;

import game.entities.Player;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Camera {
    private int x, y;

    public Camera(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void centerOn(Player player) {
        x = player.getX() - 400 + 16;
        y = player.getY() - 300 + 16;
    }
}
