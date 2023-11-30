package object;

import entity.Projectile;
import main.GamePanel;

public class OBJ_Slimeball extends Projectile {

    GamePanel gp;

    public OBJ_Slimeball(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Slimeball";
        speed = 5;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();

    }

    public void getImage() {

        up1 = setup("/projectiles/slimeball");
        up2 = setup("/projectiles/slimeball");
        down1 = setup("/projectiles/slimeball");
        down2 = setup("/projectiles/slimeball");
        left1 = setup("/projectiles/slimeball");
        left2 = setup("/projectiles/slimeball");
        right1 = setup("/projectiles/slimeball");
        right2 = setup("/projectiles/slimeball");

    }
}
