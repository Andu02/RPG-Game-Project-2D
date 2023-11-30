package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe_Normal extends Entity {

    public OBJ_Axe_Normal(GamePanel gp) {
        super(gp);

        type = type_axe;
        name = "Normal Axe";
        down1 = setup("/objects/axe_normal");
        attackValue = 0;
        description = "[" + name + "]\nAn old axe. \nChop chop time!";
        pickable = true;

    }
}
