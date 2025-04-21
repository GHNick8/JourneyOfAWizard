package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;
import java.io.InputStream;

public class UI {
    GamePanel gamePanel;
    Graphics2D g2;

    Font maruMonica;
    // Font arial_40;
    // BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    // int messageCounter = 0;
    public String currentDialogue = "";
    public int commandNum = 0;
    // Timer: time played 
    // double playTime;
    // DecimalFormat dF = new DecimalFormat("#0.00");
    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        
        try {
            InputStream inputStream = getClass().getResourceAsStream("/resources/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, inputStream);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        //arial_40 = new Font("Arial", Font.PLAIN, 40);
        // arial_40 = new Font("Cambria", Font.PLAIN, 40);
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
        g2.setFont(maruMonica);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.white);

        // Title State
        if (gamePanel.gameState == gamePanel.titleState) {
            drawTitleScreen();
        }

        // Play State 
        if (gamePanel.gameState == gamePanel.playState) {
            // TODO 
        }
        // Pause State 
        if (gamePanel.gameState == gamePanel.pauseState) {
            drawPauseScreen();
        }
        // Dialogue State 
        if (gamePanel.gameState == gamePanel.dialogueState) {
            drawDialogueScreen();
        }
    }

    public void drawTitleScreen() {

        // Title color 
        g2.setColor(new Color(204, 153, 0)); // RGB Color Picker 
        g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        // Title name 
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 95f));
        String text = "Journey Of A Wizard";

        int x = getXforCenteredText(text);
        int y = gamePanel.tileSize*3;

        // Shadow
        g2.setColor(Color.black);
        g2.drawString(text, x+5, y+5);

        // Main Color 
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        // Title image 
        x = gamePanel.screenWidth/2 - (gamePanel.tileSize*2)/2;
        y += gamePanel.tileSize*2;
        g2.drawImage(gamePanel.player.down1, x, y, gamePanel.tileSize*2, gamePanel.tileSize*2, null);

        // Menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48f));
        
        text = "NEW GAME";
        x = getXforCenteredText(text);
        y += gamePanel.tileSize*3.5;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x-gamePanel.tileSize, y);
        }

        text = "LOAD GAME";
        x = getXforCenteredText(text);
        y += gamePanel.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x-gamePanel.tileSize, y);
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

    public void drawDialogueScreen() {
        /* 
        // Window dialogue 
        int x = gamePanel.tileSize*2;
        int y = gamePanel.tileSize/2;
        int width = gamePanel.screenWidth - (gamePanel.tileSize*4);
        int height = gamePanel.tileSize*4;

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32f));

        x += gamePanel.tileSize;
        y += gamePanel.tileSize;

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
        */
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        /* 
        Color c = new Color(0, 0, 0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
        */
    }

    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gamePanel.screenWidth/2 - length/2;
        return x;
    }
}
