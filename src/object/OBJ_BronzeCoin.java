package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_BronzeCoin extends Entity {

    GamePanel gp;

    public OBJ_BronzeCoin(GamePanel gp) {
        super(gp);
        this.gp = gp;
        setDefaultValues();
    }

    public void setDefaultValues() {

        type = type_pickUpOnly;
        name = "Bronze Coin";
        down1 = setup("/objects/coin_bronze");
        value = 1;

    }

    public void use() {

        gp.ui.addMessage(name + " + " + value);
        gp.player.coin += value;

    }

}
