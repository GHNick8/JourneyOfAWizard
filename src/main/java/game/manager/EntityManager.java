// Reviewed & improved version
package game.manager;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import game.entities.Enemy;
import game.entities.Entity;
import game.entities.Item;
import game.entities.NPC;
import game.entities.Player;
import game.entities.Projectile;
import game.graphics.Camera;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityManager {

    private int score = 0;

    private List<Entity> entities;
    private List<Item> items;
    private List<Projectile> projectiles;

    private Camera camera;
    private Player player;

    public EntityManager(Camera camera) {
        this.entities = new ArrayList<>();
        this.items = new ArrayList<>();
        this.projectiles = new ArrayList<>();
        this.camera = camera;
    }

    public void addEntity(Entity entity) {
        if (entity instanceof Player p) {
            this.player = p;
        }
        entities.add(entity);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void addProjectile(Projectile projectile) {
        projectiles.add(projectile);
    }

    public void update() {
        // Update all entities
        for (Entity entity : entities) {
            entity.update();
        }

        // Update and handle item pickup
        Iterator<Item> itemIter = items.iterator();
        while (itemIter.hasNext()) {
            Item item = itemIter.next();
            if (!item.isCollected() && player.getBounds().intersects(item.getBounds())) {
                item.collect();
                switch (item.getType()) {
                    case "coin" -> score += 10;
                    case "heart" -> player.heal(1);
                }
            }
        }

        // Update and clean up projectiles
        Iterator<Projectile> projIter = projectiles.iterator();
        while (projIter.hasNext()) {
            Projectile p = projIter.next();
            p.update();
            if (!p.isActive()) {
                projIter.remove();
            }
        }
    }

    public void render(Graphics g) {
        for (Item item : items) {
            item.render(g);
        }
        for (Entity entity : entities) {
            entity.render(g);
        }
        for (Projectile p : projectiles) {
            p.render(g);
        }
    }

    public boolean checkPlayerEnemyCollision() {
        for (Entity entity : entities) {
            if (entity instanceof Enemy enemy) {
                if (player.getBounds().intersects(enemy.getBounds())) {
                    return true;
                }
            }
        }
        return false;
    }

    public NPC getNearbyNPC(Player player) {
        for (Entity e : entities) {
            if (e instanceof NPC npc) {
                if (player.isNear(npc)) {
                    return npc;
                }
            }
        }
        return null;
    }

    public void reset() {
        entities.clear();
        items.clear();
        projectiles.clear();
        score = 0;
        player = null;
    }
}


/*package game.manager;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import game.entities.Enemy;
import game.entities.Entity;
import game.entities.Item;
import game.entities.NPC;
import game.entities.Player;
import game.entities.Projectile;
import game.graphics.Camera;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityManager {
    private int score = 0;

    private List<Entity> entities;
    private List<Item> items;
    private List<Projectile> projectiles;

    private Camera camera;
    private Player player;

    public EntityManager(Camera camera) {
        this.entities = new ArrayList<>();
        this.items = new ArrayList<>();
        this.projectiles = new ArrayList<>();
        this.camera = camera;
    }

    public void addEntity(Entity entity) {
        if (entity instanceof Player player1) {
            this.player = player1;
        }
        entities.add(entity);

        // For loop collecting items & added value
        for (Item item : items) {
            if (!item.isCollected() && player.getBounds().intersects(item.getBounds())) {
                item.collect();
                if (item.getType().equals("coin")) {
                    score += 10;
                } else if (item.getType().equals("heart")) {
                    player.heal(1);
                }
            }
        }

        // Projectile logic
        Iterator<Projectile> iter = projectiles.iterator();
        while (iter.hasNext()) {
            Projectile p = iter.next();
            p.update();
            if (!p.isActive()) {
                iter.remove();
            }
        }
    }

    public void render(Graphics g) {
        for (Entity entity : entities) {
            entity.render(g);
        }
        for (Item item : items) {
            item.render(g);
        }
        for (Projectile p : projectiles) {
            p.render(g);
        }
    }

    // For loop if Player collides with Enemy
    public boolean checkPlayerEnemyCollision() {
        for (Entity entity : entities) {
            if (entity instanceof Enemy enemy) {
                if (player.getBounds().intersects(enemy.getBounds())) {
                    return true;
                }
            }
        }
        return false;
    }

    // For loop if Player gets nearby NPC 
    public NPC getNearbyNPC(Player player) {
        for (Entity e : entities) {
            if (e instanceof NPC npc) {
                if (player.isNear(npc)) {
                    return npc;
                }
            }
        }
        return null;
    }

    public void reset() {
        entities.clear();
        items.clear();
        projectiles.clear();
        score = 0;
    }
}
*/