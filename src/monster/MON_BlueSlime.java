package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_BronzeCoin;
import object.OBJ_Heart;
import object.OBJ_Mana;
import object.OBJ_Slimeball;

import java.util.Random;

public class MON_BlueSlime extends Entity {

    GamePanel gp;

    public MON_BlueSlime(GamePanel gp) {
        super(gp);
        this.gp = gp;
        getImage();
        setDefaultValues();
    }

    public void getImage() {

        up1 = setup("/monsters/slime1");
        up2 = setup("/monsters/slime2");
        down1 = setup("/monsters/slime1");
        down2 = setup("/monsters/slime2");
        left1 = setup("/monsters/slime1");
        left2 = setup("/monsters/slime2");
        right1 = setup("/monsters/slime1");
        right2 = setup("/monsters/slime2");

    }

    public void setDefaultValues() {

        type = type_monster;
        name = "Blue Slime";
        speed = 1;
        maxLife = 4;
        life = maxLife;
        attack = 2;
        defense = 0;
        exp = 1;
        projectile = new OBJ_Slimeball(gp);

        collisionArea.x = 3;
        collisionArea.y = 18;
        collisionArea.width = 42;
        collisionArea.height = 30;
        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;

    }

    public void setAction() {

        invincibleCounter(40);

        actionLockCounter++;

        if (actionLockCounter == 120) {

            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }

            actionLockCounter = 0;

        }

        int i = new Random().nextInt(100) + 1;
        if(i > 99 && !projectile.alive && shotAvailableCounter == 60 ) {

            projectile.set(worldX, worldY, direction, true, this);
            gp.projectileList.add(projectile);
            shotAvailableCounter = 0;

        }

    }

    public void damageReaction(GamePanel gp) {

        actionLockCounter = 0;
        direction = gp.player.direction;

    }

    public void checkDrop() {

        int i = new Random().nextInt(100) + 1;

        if (i < 25) {
            dropItem(new OBJ_BronzeCoin(gp));
        }
        if (i >= 25 && i < 50) {
            dropItem(new OBJ_Heart(gp));
        }
        if (i >= 50 && i < 75) {
            dropItem(new OBJ_Mana(gp));
        }
        if (i >= 75) {
            // nothing
        }
    }
}
