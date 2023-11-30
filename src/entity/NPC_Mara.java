package entity;

import main.GamePanel;

import java.util.Random;

public class NPC_Mara extends Entity {

    public NPC_Mara(GamePanel gp) {
        super(gp);

        setDefaultValues();
        getImage();
        setDialogue();

    }

    public void getImage () {

        up1 = setup("/npc/npc_mara_up1");
        up2 = setup("/npc/npc_mara_up1");
        down1 = setup("/npc/npc_mara_down1");
        down2 = setup("/npc/npc_mara_down1");
        left1 = setup("/npc/npc_mara_left1");
        left2 = setup("/npc/npc_mara_left1");
        right1 = setup("/npc/npc_mara_right1");
        right2 = setup("/npc/npc_mara_right1");

    }

    public void setAction() {

        actionLockCounter++;

        if (actionLockCounter == 120) {

            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }

            actionLockCounter = 0;

        }

    }

    public void setDefaultValues() {
        name = "MARA";
    }

    public void setDialogue() {

        type = type_npc;
        dialogues[0] = "Pirt!";

    }

    public void speak() {

        super.speak();

    }

}
