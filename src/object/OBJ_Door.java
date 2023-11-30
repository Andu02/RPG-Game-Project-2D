package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.util.Objects;

public class OBJ_Door extends Entity {

    public OBJ_Door(GamePanel gp) {

        super(gp);

        type = type_obstacle;
        name = "Door";
        down1 = setup("/objects/door");
        collision = true;
    }

    public void open(GamePanel gp) {
        if(collision) {
            name = "Opened Door";
            down1 = setup("/objects/opened_door");
            collision = false;
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You used a key and opened the door!";
            gp.ui.addMessage("Key - 1");
            gp.player.keys--;
        }
    }

}
