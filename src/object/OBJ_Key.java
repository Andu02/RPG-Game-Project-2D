package object;

import entity.Entity;
import main.CollisionChecker;
import main.GamePanel;

public class OBJ_Key extends Entity {

    CollisionChecker collisionChecker;

    public OBJ_Key(GamePanel gp) {

        super(gp);

        type = type_consumable;
        name = "Key";
        down1 = setup("/objects/key");
        description = "[" + name + "]\nIt opens a door.";
        pickable = true;

    }

    public void use(GamePanel gp) {

        used = true;

    }

}
