// Reviewed & improved version
package game.entities;

import java.awt.Color;
import java.awt.Graphics;

import game.graphics.Camera;

public class NPC extends Entity {
    private String[] dialog;
    private boolean showDialog = false;
    private int currentLine = 0;

    public NPC(int x, int y, int width, int height, Camera camera, String[] dialog) {
        super(x, y, width, height, camera);
        this.dialog = dialog;
    }

    @Override
    public void update() {
        // You could handle idle animations or dialog timing here later
    }

    public void toggleDialog() {
        showDialog = !showDialog;
        if (showDialog) currentLine = 0;
    }

    public boolean isDialogVisible() {
        return showDialog;
    }

    public String[] getDialog() {
        return dialog;
    }

    public void setDialog(String[] dialog) {
        this.dialog = dialog;
    }

    public String getCurrentLine() {
        if (dialog == null || dialog.length == 0) return "";
        return dialog[currentLine];
    }

    public void nextLine() {
        if (dialog != null && currentLine < dialog.length - 1) {
            currentLine++;
        } else {
            showDialog = false; // end of conversation
        }
    }

    public void interact() {
        // Future method for triggering quests, shops, etc.
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.ORANGE);
        graphics.fillRect(x - camera.getX(), y - camera.getY(), width, height);

        // Optional: Draw dialog box here in future
        // if (showDialog) {
        //     graphics.setColor(Color.WHITE);
        //     graphics.drawString(getCurrentLine(), 10, 10); // Simplified example
        // }
    }
}


/*package game.entities;

import java.awt.Color;
import java.awt.Graphics;

import game.graphics.Camera;

public class NPC extends Entity{
    private String[] dialog;
    private boolean showDialog = false;

    public NPC(int x, int y, int width, int height, Camera camera, String[] dialog) {
        super(x, y, width, height, camera);
        this.dialog = dialog;
    }

    @Override
    public void update() {}

    public void toggleDialog() {
        showDialog =!showDialog;
    }

    public boolean isDialogVisible() {
        return showDialog;
    }

    public String[] getDialog() {
        return dialog;
    }

    public void setDialog(String[] dialog) {
        this.dialog = dialog;
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.ORANGE);
        graphics.fillRect(x - camera.getX(), y - camera.getY(), width, height);
    }
}
*/