package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_HealthPotion extends Entity {

    GamePanel gp;

    public OBJ_HealthPotion(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_consumable;
        name = "Health Potion";
        value = 5;
        down1 = setup("/objects/health_potion");
        description = "[" + name + "]\nA health potion. \nFor restoring 5 life!";
        pickable = true;

    }

    public void use() {

        gp.gameState = gp.dialogueState;
        if(gp.player.life == gp.player.maxLife) {
            gp.ui.currentDialogue = "Your life is already full!";
        }
        else {
            used = true;
            gp.ui.currentDialogue = "Your life has been restored by " + value + "!";
            gp.player.life += value;

        }

    }

}
