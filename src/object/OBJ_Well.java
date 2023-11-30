package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Well extends Entity {

    public OBJ_Well(GamePanel gp) {

        super(gp);
        getImage();
        setDefaultValues(gp);
    }

    public void getImage() {
        down1 = setup("/objects/well", 16, 32);
    }

    public void setDefaultValues(GamePanel gp) {
        name = "Well";

        collision = true;

        collisionArea.x = 0;
        collisionArea.y = gp.tileSize;
        collisionArea.width = gp.tileSize;
        collisionArea.height = gp.tileSize;
        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;
    }

}

