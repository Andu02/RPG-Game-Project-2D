package main;
import entity.Entity;
import entity.NPC_Mara;
import entity.Player;
import entity.Projectile;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 pixels tile
    public final int scale = 3;

    public final int tileSize = originalTileSize * scale; //48x48 pixels tile
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // WORLD SETTINGS
    public final int maxWorldCol = 60;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxScreenCol;
    public final int worldHeight = tileSize * maxScreenRow;

    // FULLSCREEN
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;

    // FPS
    int FPS = 60;

    // TILES
    TileManager tileM = new TileManager(this);

    // CONTROLS
    KeyHandler keyH = new KeyHandler(this);

    // SOUND
    Sound music = new Sound();
    Sound se = new Sound();

    // EVENT
    public EventHandler eventHandler = new EventHandler(this);

    // CONFIG
    Config config = new Config(this);

    // TIME
    Thread gameThread;

    // COLLISION
    public CollisionChecker collisionChecker = new CollisionChecker(this);

    // PLAYER
    public Player player = new Player(this, keyH);

    // ENTITY AND OBJECTS
    public AssetManager assetManager = new AssetManager(this);
    public Entity[] obj = new Entity[20];
    public Entity[] npc = new Entity[10];
    public Entity[] monsters = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList<>();
    public ArrayList<Entity> projectileList = new ArrayList<>();

    // MARA :)
    NPC_Mara Mara = new NPC_Mara(this);

    // UI
    public UI ui = new UI(this);

    // GAME STATE
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int titleState = 0;
    public int characterState = 4;
    public int gameOverState = 5;

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }

    public void setupGame() {

        assetManager.setObject();

        assetManager.setNPC();

        assetManager.setMonster();

        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D)tempScreen.getGraphics();

    }

    public void retry() {

        player.setDefaultPosition();
        player.restoreLifeAndMana();
        assetManager.setNPC();
        assetManager.setMonster();
        player.invincible = false;
    }

    public void restart() {

        player.setDefaultValues();
        player.setItems();
        assetManager.setObject();
        assetManager.setNPC();
        assetManager.setMonster();
        player.invincible = false;
    }

    public void setFullScreen() {

        // GET LOCAL SCREEN DEVICE
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();

    }

    public void setWindowed() {

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(null);
        screenWidth2 = screenWidth;
        screenHeight2 = screenHeight;

    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();

    }

    public void stopGameThread() {

        gameThread = null;

    }

    @Override
    public void run() {

        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if (delta >= 1) {
                update();
                drawToTempScreen(); // draw everything to the buffered image
                drawToScreen(); // draw the buffered image to the screen
                delta--;
            }
        }
    }

    public void update() {

        if (gameState == playState) {
            // PLAYER
            player.update();

            // NPC
            for (Entity npc : npc) {
                if (npc != null) {
                    npc.update();
                }
            }

            // MONSTERS
            for (int i = 0; i < monsters.length; i++) {
                if (monsters[i] != null) {
                    if(monsters[i].alive && !monsters[i].dying) {
                        monsters[i].update();
                    }
                    if(!monsters[i].alive) {
                        monsters[i].checkDrop();
                        monsters[i] = null;
                    }
                }

            }

            // PROJECTILE
            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    if(projectileList.get(i).alive) {
                        projectileList.get(i).update();
                    }
                    if(!projectileList.get(i).alive) {
                        projectileList.remove(i);
                    }
                }

            }

            if (gameState == pauseState) {

            }
            if (gameState == dialogueState) {

            }
            if (gameState == titleState) {

            }
            if(gameState == characterState) {

            }
            if(gameState == gameOverState) {

            }
        }

        player.checkHealthAndMana();

    }

    public void drawToTempScreen() {

        // DEBUG
        long drawStart = 0;
        if (keyH.checkDrawTime) {
            drawStart = System.nanoTime();
        }

        // TITLE SCREEN
        if (gameState == titleState) {
            ui.draw(g2);
        }
        // PLAY STATE
        else {

            // TILE
            tileM.draw(g2);

            // ADD ENTITIES TO THE LIST
            entityList.add(player);

            for (Entity npc : npc) {
                if (npc != null) {
                    entityList.add(npc);
                }
            }

            for (Entity monster : monsters) {
                if (monster != null) {
                    entityList.add(monster);
                }
            }

            for (Entity obj : obj) {
                if (obj != null) {
                    entityList.add(obj);
                }
            }

            for(Entity projectile : projectileList) {
                if(projectile != null) {
                    entityList.add(projectile);
                }
            }

            // SORT
            entityList.sort(Comparator.comparingInt(e -> e.worldY));

            // DRAW ENTITIES
            for (Entity entity : entityList) {
                entity.draw(g2);
            }

            // EMPTY ENTITY LIST
            entityList.clear();

            // UI
            ui.draw(g2);

            // DEBUG
            if (keyH.checkDrawTime) {
                long drawEnd = System.nanoTime();
                long passed = drawEnd - drawStart;
                g2.setColor(Color.white);

                g2.drawString("WorldX " + player.worldX, 10, 440);
                g2.drawString("WorldY: " + player.worldY, 10, 470);
                g2.drawString("Col: " + player.worldX / tileSize, 10, 500);
                g2.drawString("Row: " + player.worldY / tileSize, 10, 530);
                g2.drawString("Draw time: " + passed, 10, 560);
            }
        }

    }

    public void drawToScreen() {

        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();

    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic(){
        music.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }



}
