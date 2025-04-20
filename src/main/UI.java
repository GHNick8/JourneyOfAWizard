package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.text.DecimalFormat;

public class UI {
    GamePanel gamePanel;
    Graphics2D g2;
    Font arial_40;
    // BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    // Timer: time played 
    double playTime;
    DecimalFormat dF = new DecimalFormat("#0.00");
    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        // OBJ_Key key = new OBJ_Key(gamePanel);
        // keyImage = key.image;
    }
    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }
    public void draw(Graphics2D g2) {
        /*

        Previous used code 

        g2.setFont(arial_40);
        g2.setColor(Color.white);
        g2.drawImage(keyImage, gamePanel.tileSize/2, gamePanel.tileSize/2, gamePanel.tileSize, gamePanel.tileSize, null);
        g2.drawString("x " +  gamePanel.player.hasKey, 74, 65 );
        // Timer: time played
        playTime += (double)1/60;
        g2.drawString("Time played:"+dF.format(playTime), gamePanel.tileSize*8, 65);
        // Message 
        if (messageOn == true) {
            g2.setFont(g2.getFont().deriveFont(30F));
            g2.drawString(message, gamePanel.tileSize/2, gamePanel.tileSize*5);
            messageCounter++;
            // Message display 2 sec 
            if (messageCounter > 120) {
                messageCounter = 0;
                messageOn = false;
            }
        }
        */

        // New code 
        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);

        if (gamePanel.gameState == gamePanel.playState) {
            // TODO 
        }
        if (gamePanel.gameState == gamePanel.pauseState) {
            drawPauseScreen();
        }
    }

    public void drawPauseScreen() {
        // Change size font 
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80f));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gamePanel.screenHeight/2;
        g2.drawString(text, x, y);
    }

    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gamePanel.screenWidth/2 - length/2;
        return x;
    }
}
