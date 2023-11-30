package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_ManaPotion extends Entity {

    GamePanel gp;

    public OBJ_ManaPotion(GamePanel gp) {
        super(gp);
        this.gp = gp;
        setDefaultValues();
        getImage();
    }

    public void setDefaultValues() {
        type = type_consumable;
        name = "Mana Potion";
        value = 3;
        pickable = true;
    }

    public void getImage() {
        down1 = setup("/objects/mana_potion");
        description = "[" + name + "]\nA health potion. \nFor restoring 3 mana!";
    }

    public void use() {

        gp.gameState = gp.dialogueState;
        if(gp.player.mana == gp.player.maxMana) {
            gp.ui.currentDialogue = "Your mana is already full!";
        }
        else {
            used = true;
            gp.ui.currentDialogue = "Your mana has been restored by " + value + "!";
            gp.player.mana += value;
        }

    }

}
