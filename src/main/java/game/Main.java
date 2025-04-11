package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import game.entities.Enemy;
import game.entities.NPC;
import game.entities.Player;
import game.graphics.Camera;
import game.graphics.TileMap;
import game.input.Keyboard;
import game.manager.EntityManager;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Main extends Canvas implements Runnable {
    protected JFrame frame;
    protected boolean running = false;
    protected Thread thread;

    protected TileMap tileMap;
    protected Camera camera;
    protected EntityManager entityManager;

    protected Scene currentScene = Scene.MENU;
    protected boolean displayInitialized = false;

    protected Sound titleScreen;
    protected Sound bgm;
    protected Sound gameOverSound;
    protected boolean gameOverPlayed = false;

    // Interaction cooldown for dialog toggle
    private long lastInteraction = 0;
    private final long interactionCooldown = 300;

    protected enum Scene {
        MENU,
        GAME,
        GAME_OVER
    }

    public Main() {
        setPreferredSize(new Dimension(800, 600));
        addKeyListener(new Keyboard());
        setFocusable(true);

        camera = new Camera(0, 0);
        setupGame();

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public final void setupGame() {
        tileMap = new TileMap();
        entityManager = new EntityManager(camera);

        Player player = new Player(100, 100, tileMap, camera);
        player.setEntityManager(entityManager);
        entityManager.addEntity(player);

        NPC npc = new NPC(200, 100, 32, 36, camera, new String[]{
            "Welcome to Journey Of A Wizard!", "Stay vigilant!"
        });
        entityManager.addEntity(npc);

        Enemy enemy = new Enemy(400, 300, tileMap, camera);
        entityManager.addEntity(enemy);
    }

    public synchronized void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D / 60D;

        while (running) {
            long now = System.nanoTime();
            if ((now - lastTime) > nsPerTick) {
                update();
                render();
                lastTime = now;
            }
        }
    }

    private void update() {
        if (null != currentScene)
            switch (currentScene) {
                case MENU -> {
                    if (Keyboard.keys[KeyEvent.VK_ENTER]) {
                        currentScene = Scene.GAME;
                        if (titleScreen != null) titleScreen.stop();
                        if (bgm != null && !bgm.isPlaying()) bgm.loop();
                    }
                }
                case GAME -> {
                    if (entityManager != null) {
                        entityManager.update();
                        camera.centerOn(entityManager.getPlayer());

                        NPC npc = entityManager.getNearbyNPC(entityManager.getPlayer());
                        if (npc != null && Keyboard.keys[KeyEvent.VK_E]) {
                            long now = System.currentTimeMillis();
                            if (now - lastInteraction >= interactionCooldown) {
                                npc.toggleDialog();
                                lastInteraction = now;
                            }
                        }

                        if (entityManager.checkPlayerEnemyCollision()) {
                            entityManager.getPlayer().damage(1);
                            if (entityManager.getPlayer().getHealth() <= 0) {
                                currentScene = Scene.GAME_OVER;
                                if (!gameOverPlayed) {
                                    if (gameOverSound != null) gameOverSound.play();
                                    if (bgm != null) bgm.stop();
                                    gameOverPlayed = true;
                                }
                            }
                        }
                    }
                }
                case GAME_OVER -> {
                    if (Keyboard.keys[KeyEvent.VK_R]) {
                        setupGame();
                        currentScene = Scene.GAME;
                        gameOverPlayed = false;
                    }
                }
                default -> {
                }
            }
    }

    private void initDisplay() {
        if (!displayInitialized && isDisplayable()) {
            createBufferStrategy(3);
            displayInitialized = true;
        }
    }

    private void render() {
        initDisplay();
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) return;

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (tileMap != null && camera != null) {
            tileMap.render(g, camera);
        }

        if (entityManager != null) {
            entityManager.render(g);
        }

        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.start();
    }

    public void setTitle(String title) {
        if (frame != null) {
            frame.setTitle(title);
        }
    }
}
