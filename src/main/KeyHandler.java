package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gamePanel;

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    // Debug 
    boolean checkDrawTime = false;

    // Shortcut key to pause the game 
    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {     
        int code = e.getKeyCode();

        // Play State 
        if(gamePanel.gameState == gamePanel.playState) {
            if(code == KeyEvent.VK_Z) {
                upPressed = true;
            }
            if(code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if(code == KeyEvent.VK_Q) {
                leftPressed = true;
            }
            if(code == KeyEvent.VK_D) {
                rightPressed = true;
            }
            // Pause key
            if(code == KeyEvent.VK_P) {
                gamePanel.gameState = gamePanel.pauseState;
            }
            // Enter key 
            if(code == KeyEvent.VK_ENTER) {
                enterPressed = true;
            }
    
            // Debug 
            if (code == KeyEvent.VK_T) {
                if (checkDrawTime == false) {
                    checkDrawTime = true;
                } else if (checkDrawTime == true) {
                    checkDrawTime = false;
                }
            }
        }

        // Pause State 
        else if(gamePanel.gameState == gamePanel.pauseState) {
            if(code == KeyEvent.VK_P) {
                gamePanel.gameState = gamePanel.playState;
            }
        }

        // Dialogue State 
        else if(gamePanel.gameState == gamePanel.dialogueState) {
            if(code == KeyEvent.VK_ENTER) {
                gamePanel.gameState = gamePanel.playState;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_Z) {
            upPressed = false;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if(code == KeyEvent.VK_Q) {
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }
}
