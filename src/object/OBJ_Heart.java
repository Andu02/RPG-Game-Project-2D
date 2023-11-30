package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity {

    GamePanel gp;

    public OBJ_Heart(GamePanel gp) {
        super(gp);
        this.gp = gp;
        setDefaultValues();
        getImage();
    }

    public void setDefaultValues() {
        type = type_pickUpOnly;
        name = "Heart";
        value = 2;
    }

    public void getImage() {
        down1 = setup("/objects/heart_full");
        image = setup("/objects/heart_full");
        image2 = setup("/objects/heart_half");
        image3 = setup("/objects/heart_blank");
    }

    public void use() {

        gp.ui.addMessage("Health + " + value);
        gp.player.life += value;

    }

}
