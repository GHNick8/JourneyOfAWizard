package main;

import entity.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
    final int element = 16;
    final int scale = 3;
    public final int tileSize = element * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    int FPS = 60;
    TileManager tileManager = new TileManager(this);
    KeyHandler keyHandler = new KeyHandler();
    Sound music = new Sound();
    Sound se = new Sound();
    // Public classes for global accessibility
    public CollisionDetector cd = new CollisionDetector(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;
    // Entity & Object
    public Player player = new Player(this, keyHandler);
    public SuperObject obj[] = new SuperObject[20]; // Add objects 
    // Constructor GamePanel 
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }
    public void setupGame() {
        assetSetter.setObject();
        playMusic(0);
    }
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    /*
    // Method with 2 warnings 
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (gameThread != null) {
            // Debug & Testing 
            long currentTime = System.nanoTime();
            System.out.println("current Time:"+currentTime); 
            System.out.println("Running!"); 
            update();
            repaint(); 
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;
                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    } 
    */
    // Clean method 
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        // long timer = 0;
        // long drawCount = 0;
        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            // timer += (currentTime - lastTime);
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
                // drawCount++;
            }
            /* Debug & Testing 
            if (timer >= 1000000000) {
                System.out.println("FPS:" + drawCount); // FPS:60 
                drawCount = 0;
                timer = 0;
            }
            */
        }
    }
    public void update() {
        player.update();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        tileManager.draw(g2);
        for (SuperObject obj1 : obj) {
            if (obj1 != null) {
                obj1.draw(g2, this);
            }
        }
        player.draw(g2);
        ui.draw(g2);
        g2.dispose();
    }
    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic() {
        music.stop();
    }
    // Sound Effect 
    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }
}