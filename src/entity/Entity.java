package entity;

import main.GamePanel;
import main.UtiliyTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Entity {

    GamePanel gp;

    // POSITION
    public int worldX, worldY;
    public int speed = 1;
    public String direction = "down";

    // IMAGES
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;

    public int spriteCounter = 0, attackingAnimationCounter = 0;
    public int spriteNum = 1;

    // COLLISION
    public Rectangle collisionArea = new Rectangle(8, 16, 32, 32);
    public int collisionAreaDefaultX = collisionArea.x, collisionAreaDefaultY = collisionArea.y;
    public boolean collisionOn = false;

    public int actionLockCounter = 0;

    // DIALOGUE
    String[] dialogues = new String[20];
    int dialogueIndex = 0;

    // CHARACTER STATUS
    public int maxLife;
    public int maxMana;
    public int life;
    public int mana;
    public int level = 1;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;
    public Projectile projectile;

    // ITEM ATTRIBUTES
    public int value;
    public int attackValue;
    public int defenseValue;
    public int useCost;

    // OBJECTS
    public BufferedImage image, image2, image3;
    public boolean collision = false;
    public String name;
    public String description;
    public boolean pickable = false;
    public boolean used = false;

    // COMBAT
    public boolean invincible = false;
    public int invincibleCounter = 0;
    public boolean attacking = false;
    public Rectangle attackCollision = new Rectangle(0, 0, 0, 0);
    int tempScreenX, tempScreenY;
    public boolean alive = true;
    public boolean dying = false;
    int dyingCounter = 0;
    boolean hpBarOn = false;
    int hpBarCounter = 0;
    public int shotAvailableCounter = 0;

    // TYPE
    public int type;
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_sword = 3;
    public final int type_axe = 4;
    public final int type_shield = 5;
    public final int type_consumable = 6;
    public final int type_obstacle = 7;
    public final int type_pickUpOnly = 8;

    // WORK IN PROGRESS
    public int keys = 0;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction() {

    }

    public void damageReaction(GamePanel gp) {

    }

    public void speak() {

        if(dialogues[dialogueIndex] == null){
            dialogueIndex = 0;
        }

        gp.ui.nameDialogue = name;

        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (gp.player.direction) {
            case "up": direction = "down"; break;
            case "down": direction = "up"; break;
            case "left": direction = "right"; break;
            case "right": direction = "left"; break;
        }

    }

    public void checkDrop() {
    }

    public void dropItem(Entity droppedItem) {

        for(int i = 0; i < gp.obj[gp.currentMap].length; i++) {
            if(gp.obj[gp.currentMap][i] == null) {
                gp.obj[gp.currentMap][i] = droppedItem;
                gp.obj[gp.currentMap][i].worldX = worldX;
                gp.obj[gp.currentMap][i].worldY = worldY;
                break;
            }
        }
    }

    public void update() {

        setAction();

        collisionOn = false;
        gp.collisionChecker.checkTile(this);
        gp.collisionChecker.checkObject(this, false);
        gp.collisionChecker.checkEntity(this, gp.npc);
        gp.collisionChecker.checkEntity(this, gp.monsters);
        boolean contactPlayer = gp.collisionChecker.checkPlayer(this);

        if(this.type == type_monster && contactPlayer) {
            damagePlayer(attack);
        }

        if(!collisionOn) {
            switch(direction){
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "right": worldX += speed; break;
                case "left": worldX -= speed; break;
            }
        }

        spriteCounter++;
        if (spriteCounter > 16) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        // MONSTERS HEALTH BAR DISPLAY TIME
        if(hpBarOn) {
            hpBarCounter++;
            if(hpBarCounter > 360) {
                hpBarOn = false;
                hpBarCounter = 0;
            }

        }

        // DIALOGUE NAME RESET
        if(gp.gameState != gp.dialogueState) {
            gp.ui.nameDialogue = "";
        }

        if(shotAvailableCounter < 60) {
            shotAvailableCounter++;
        }

    }

    public void damagePlayer(int attack){
        if(!gp.player.invincible) {
            int damage = attack - gp.player.defense;
            if(damage < 0) {
                damage = 0;
            }
            gp.player.life -= damage;
            gp.player.invincible = true;
        }
    }

    public void draw(Graphics2D g2, int xExtraRenderingTile, int yExtraRenderingTile) {

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (
            worldX + gp.tileSize + xExtraRenderingTile > gp.player.worldX - gp.player.screenX &&
            worldX - gp.tileSize + xExtraRenderingTile < gp.player.worldX + gp.player.screenX &&
            worldY + gp.tileSize + yExtraRenderingTile > gp.player.worldY - gp.player.screenY &&
            worldY - gp.tileSize + yExtraRenderingTile < gp.player.worldY + gp.player.screenY
        ) {

            BufferedImage image = null;
            tempScreenX = screenX;
            tempScreenY = screenY;

            switch (direction) {
                case "up":
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                    break;
                case "down":
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                    break;
                case "left":
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                    break;
                case "right":
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                    break;
            }

            // Monster HP Bar
            if(type == 2 && hpBarOn) {

                double oneScale = (double)gp.tileSize/maxLife;
                double hpBarValue = oneScale*life;

                g2.setColor(new Color(35, 35, 35));
                g2.fillRect(screenX, screenY - 15, gp.tileSize, 10);

                g2.setColor(Color.black);
                g2.setStroke(new BasicStroke(2));
                g2.drawRect(screenX, screenY - 15, gp.tileSize, 10);

                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);

            }

            if(invincible) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            }

            if(dying) {
                dyingAnimation(g2);
            }

            g2.drawImage(image, tempScreenX, tempScreenY, null);

            // RESET ALPHA
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }

    public void dyingAnimation(Graphics2D g2) {

        collisionArea = new Rectangle(0,0,0,0);

        dyingCounter++;

        int i = 5;

        if (dyingCounter <= i) { changeAlpha(g2, 0);}
        if (dyingCounter > i && dyingCounter <= i*2) { changeAlpha(g2, 1);}
        if (dyingCounter > i*2 && dyingCounter <= i*3) { changeAlpha(g2, 0);}
        if (dyingCounter > i*3 && dyingCounter <= i*4) { changeAlpha(g2, 1);}
        if (dyingCounter > i*4 && dyingCounter <= i*5) { changeAlpha(g2, 0);}
        if (dyingCounter > i*5 && dyingCounter <= i*6) { changeAlpha(g2, 1);}
        if (dyingCounter > i*6 && dyingCounter <= i*7) { changeAlpha(g2, 0);}
        if (dyingCounter > i*7 && dyingCounter <= i*8) { changeAlpha(g2, 1);}
        if (dyingCounter > i*8) {
            dying = false;
            alive = false;
        }
    }

    public void changeAlpha(Graphics2D g2, float alphaValue) {

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));

    }

    public BufferedImage setup(String imagePath) {
        UtiliyTool uTool = new UtiliyTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return image;

    }

    // OVERLOAD
    public BufferedImage setup(String imagePath, int width, int height) {
        UtiliyTool uTool = new UtiliyTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
            image = uTool.scaleImage(image, width*gp.scale, height*gp.scale);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return image;

    }

    public void setDefaultValues() {

    }

    public void use() {

    }

    public void invincibleCounter(int time) {

        if(invincible) {
            invincibleCounter++;
            if(invincibleCounter > time) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

    }

    public void open(GamePanel gp) {

    }

}
