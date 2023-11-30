package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Mana extends Entity {

    GamePanel gp;

    public OBJ_Mana(GamePanel gp) {
        super(gp);
        this.gp = gp;
        setDefaultValues();
        getImage();
    }

    public void setDefaultValues() {
        type = type_pickUpOnly;
        name = "Mana";
        value = 1;
    }

    public void getImage() {
        down1 = setup("/objects/mana_full");
        image = setup("/objects/mana_full");
        image2 = setup("/objects/mana_blank");
    }

    public void use() {

        gp.ui.addMessage("Mana + " + value);
        gp.player.mana += value;

    }

}
