package entity;
import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Fireball;
import object.OBJ_Key;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    // INVENTORY
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;

    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);

        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        collisionArea = new Rectangle(8, 16, 32, 32);

        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }

    public void setDefaultValues() {

        type = type_player;

        setDefaultPosition();
        speed = 3;

        // ATTACK
        attackCollision.width = 36;
        attackCollision.height = 36;

        // PLAYER STATUS
        level = 1;
        maxLife = 6;
        maxMana = 4;
        life = maxLife;
        mana = maxMana;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        projectile = new OBJ_Fireball(gp);
        attack = getAttack();
        defense = getDefense();

    }

    public void setDefaultPosition() {

        worldX = gp.tileSize * 28;
        worldY = gp.tileSize * 22;
        direction = "down";

    }

    public void restoreLifeAndMana() {

        life = maxLife;
        mana = maxMana;
        invincible = false;

    }

    public void setItems() {

        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);

    }

    public int getAttack() {
        return attack = currentWeapon.attackValue * strength;
    }

    public int getDefense() {
        return defense = currentShield.defenseValue * dexterity;
    }

    public void checkLevelUp() {
        if(exp == nextLevelExp) {

            level ++;
            nextLevelExp *= 2;
            maxLife += 2;
            maxMana += 1;
            strength ++;
            dexterity ++;
            attack = getAttack();
            defense = getAttack();
            exp = 0;

            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You are level " + level + " now!\nKeep going!";
        }
    }

    public void getPlayerImage() {

        up1 = setup("/player/player_up1");
        up2 = setup("/player/player_up2");
        down1 = setup("/player/player_down1");
        down2 = setup("/player/player_down2");
        left1 = setup("/player/player_left1");
        left2 = setup("/player/player_left2");
        right1 = setup("/player/player_right1");
        right2 = setup("/player/player_right2");

    }

    public void getPlayerAttackImage() {

        if(currentWeapon.type == type_sword) {
            attackUp1 = setup("/player/player_attack_up1", 16, 32);
            attackUp2 = setup("/player/player_attack_up2", 16, 32);
            attackDown1 = setup("/player/player_attack_down1", 16, 32);
            attackDown2 = setup("/player/player_attack_down2", 16, 32);
            attackLeft1 = setup("/player/player_attack_left1", 32, 16);
            attackLeft2 = setup("/player/player_attack_left2", 32, 16);
            attackRight1 = setup("/player/player_attack_right1", 32, 16);
            attackRight2 = setup("/player/player_attack_right2", 32, 16);
        }
        else if(currentWeapon.type == type_axe) {
            attackUp1 = setup("/player/player_attack_axe_up1", 16, 32);
            attackUp2 = setup("/player/player_attack_axe_up2", 16, 32);
            attackDown1 = setup("/player/player_attack_axe_down1", 16, 32);
            attackDown2 = setup("/player/player_attack_axe_down2", 16, 32);
            attackLeft1 = setup("/player/player_attack_axe_left1", 32, 16);
            attackLeft2 = setup("/player/player_attack_axe_left2", 32, 16);
            attackRight1 = setup("/player/player_attack_axe_right1", 32, 16);
            attackRight2 = setup("/player/player_attack_axe_right2", 32, 16);
        }

    }

    public void update() {

        if(attacking) {
            attacking();
        }
        else if (keyH.downPressed || keyH.leftPressed || keyH.upPressed || keyH.rightPressed || keyH.enterPressed) {

            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.rightPressed) {
                direction = "right";
            } else if (keyH.leftPressed) {
                direction = "left";
            }

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.collisionChecker.checkTile(this);

            // CHECK NPC COLLISION
            int npcIndex = gp.collisionChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // CHECK MONSTER COLLISION
            int monsterIndex = gp.collisionChecker.checkEntity(this, gp.monsters);
            contactMonster(monsterIndex);

            // CHECK EVENT
            if(gp.eventHandler.checkEvent()) {
                attacking = false;
            }

            // CHECK OBJECT COLLISION
            int objIndex = gp.collisionChecker.checkObject(this, true);
            interactObject(objIndex);

            if (!collisionOn && !keyH.enterPressed ) {
                switch (direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "right": worldX += speed; break;
                    case "left": worldX -= speed; break;
                }
            }

            keyH.enterPressed = false;

            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }

        }

        if(keyH.shotKeyPressed && !projectile.alive && shotAvailableCounter == 60 && gp.player.mana != 0) {

            projectile.set(worldX, worldY, direction, true, this);

            gp.projectileList.add(projectile);

            gp.player.mana -= projectile.useCost;

            shotAvailableCounter = 0;

        }

        // COMBAT
        invincibleCounter(60);

        if(shotAvailableCounter < 60) {
            shotAvailableCounter++;
        }

        // GAME OVER
        if(life <= 0) {
            gp.stopMusic();
            gp.gameState = gp.gameOverState;
        }

    }

    // COMBAT
    public void attacking() {

        attackingAnimationCounter++;
        if (attackingAnimationCounter > 10) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }

            // SAVE
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int collisionAreaWidth = collisionArea.width;
            int collisionAreaHeight = collisionArea.height;

            //
            switch (direction) {
                case "up": worldY -= attackCollision.height; break;
                case "down": worldY += attackCollision.height; break;
                case "left": worldX -= attackCollision.width; break;
                case "right": worldX += attackCollision.width; break;
            }

            // ATTACK AREA BECOMES SOLID AREA
            collisionArea.width = attackCollision.width;
            collisionArea.height = attackCollision.height;

            // CHECK COLLISION WITH MONSTERS
            int monsterIndex = gp.collisionChecker.checkEntity(this, gp.monsters);

            damageMonster(monsterIndex, attack);

            // RESTORE
            worldX = currentWorldX;
            worldY = currentWorldY;
            collisionArea.width = collisionAreaWidth;
            collisionArea.height = collisionAreaHeight;

            attackingAnimationCounter = 0;
            attacking = false;
        }

    }

    public void interactObject(int i) {
        if (i != 999) {

            // PICKUP ONLY ITEMS
            if (gp.obj[i].type == type_pickUpOnly) {

                gp.obj[i].use();
                gp.obj[i] = null;

            }
            // PICKABLE ITEMS
            else if (gp.obj[i].pickable) {
                if (inventory.size() != maxInventorySize) {

                    inventory.add(gp.obj[i]);
                    gp.ui.addMessage("You got a " + gp.obj[i].name + "!");
                } else {
                    gp.ui.addMessage("You cannot carry any more!");
                }

                // KEY - WORK IN PROGRESS
                if (gp.obj[i].name.equals("Key")) keys++;

                gp.obj[i] = null;

            }
            // OBSTACLES
            else if (gp.obj[i].type == type_obstacle) {
                switch (gp.obj[i].name) {
                    // DOOR
                    case "Door":
                        for (int j = 0; j < inventory.size(); j++) {
                            if (inventory.get(j).name.equals("Key")) {
                                if (keys > 0) {
                                    gp.obj[i].open(gp);
                                    inventory.remove(j);
                                    break;
                                }
                            }
                            else {
                                gp.gameState = gp.dialogueState;
                                gp.ui.currentDialogue = "The door is locked!";
                            }
                        }
                        break;
                    case "":
                        break;
                }
            }
        }
    }

    public void interactNPC(int i) {

        if (keyH.enterPressed) {

            if (i != 999) {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
            else {
                attacking = true;
            }

        }
    }

    public void contactMonster (int i){
        if (i != 999) {

            if(!invincible) {

                int damage = gp.monsters[i].attack - defense;
                if(damage < 0) {
                    damage = 0;
                }

                life -= damage;
                invincible = true;
            }

        }
    }

    public void damageMonster(int i, int attack) {
        if (i != 999) {
            if (!gp.monsters[i].invincible) {

                int damage = attack - gp.monsters[i].defense;
                if(damage < 0) {
                    damage = 0;
                }

                gp.monsters[i].life -= damage;
                gp.ui.addMessage(damage + " damage!");

                gp.monsters[i].damageReaction(gp);

                gp.monsters[i].invincible = true;
                gp.monsters[i].hpBarOn = true;
            }
            if (gp.monsters[i].life <= 0) {
                gp.monsters[i].dying = true;
                gp.player.exp += gp.monsters[i].exp;
                gp.ui.addMessage("You killed the " + gp.monsters[i].name + "!");
                gp.ui.addMessage("Experience + " + gp.monsters[i].exp );
                checkLevelUp();
            }
        }
    }

    public void selectItem() {

        int itemIndex = gp.ui.getItemIndexOnSlot();

        if (itemIndex < inventory.size()) {

            Entity selectedItem = inventory.get(itemIndex);

            switch (selectedItem.type) {
                case (type_sword), (type_axe):
                    currentWeapon = inventory.get(itemIndex);
                    attack = getAttack();
                    getPlayerAttackImage();
                    break;
                case (type_shield):
                    currentShield = inventory.get(itemIndex);
                    getDefense();
                    getPlayerAttackImage();
                    break;
                case (type_consumable):
                    selectedItem.use();
                    if(selectedItem.used) {
                        inventory.remove(itemIndex);
                    }
                    break;
            }

        }
    }

    public void draw(Graphics2D g2){

        BufferedImage image = null;

        tempScreenX = screenX;
        tempScreenY = screenY;

        switch (direction) {
            case "up":
                if (!attacking) {
                    if (spriteNum == 1) {image = up1;}
                    if (spriteNum == 2) {image = up2;}
                }
                if (attacking) {
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) {image = attackUp1;}
                    if (spriteNum == 2) {image = attackUp2;}
                }
                break;
            case "down":
                if (!attacking) {
                    if (spriteNum == 1) {image = down1;}
                    if (spriteNum == 2) {image = down2;}
                }
                if (attacking) {
                    if (spriteNum == 1) {image = attackDown1;}
                    if (spriteNum == 2) {image = attackDown2;}
                }
                break;
            case "left":
                if (!attacking) {
                    if (spriteNum == 1) {image = left1;}
                    if (spriteNum == 2) {image = left2;}
                }
                if (attacking) {
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1) {image = attackLeft1;}
                    if (spriteNum == 2) {image = attackLeft2;}
                }
                break;
            case "right":
                if (!attacking) {
                    if (spriteNum == 1) {image = right1;}
                    if (spriteNum == 2) {image = right2;}
                }
                if (attacking) {
                    if (spriteNum == 1) {image = attackRight1;}
                    if (spriteNum == 2) {image = attackRight2;}
                }
                break;
        }

        if(invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }

        g2.drawImage(image, tempScreenX, tempScreenY, null);

        // RESET ALPHA
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }

    public void checkHealthAndMana() {
        if(life > maxLife) {
            life = maxLife;
        }

        if(mana > maxMana) {
            mana = maxMana;
        }
    }

}