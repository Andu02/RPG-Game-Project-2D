package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Hole extends Entity {

    GamePanel gp;

    public OBJ_Hole(GamePanel gp) {

        super(gp);
        this.gp = gp;
        getImage();
        setDefaultValues();
    }

    public void getImage() {
        down1 = setup("/objects/hole");
    }

    public void setDefaultValues() {
        name = "Hole";
    }

}

