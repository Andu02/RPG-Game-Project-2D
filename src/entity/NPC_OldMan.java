package entity;

import main.GamePanel;

import java.util.Random;

public class NPC_OldMan extends Entity {

    public NPC_OldMan(GamePanel gp) {

        super(gp);

        setDefaultValues();
        getImage();
        setDialogue();

    }

    public void setDefaultValues() {
        type = type_npc;
        name = "OLD MAN";
    }

    public void getImage () {

        up1 = setup("/npc/npc_oldman_up1");
        up2 = setup("/npc/npc_oldman_up1");
        down1 = setup("/npc/npc_oldman_down1");
        down2 = setup("/npc/npc_oldman_down1");
        left1 = setup("/npc/npc_oldman_left1");
        left2 = setup("/npc/npc_oldman_left1");
        right1 = setup("/npc/npc_oldman_right1");
        right2 = setup("/npc/npc_oldman_right1");

    }

    public void setDialogue() {

        dialogues[0] = "Hello, young lad.";
        dialogues[1] = "So I guess you are here for the treasure?";
        dialogues[2] = "I used to be a great wizard but now... \nI'm a little bit too old for taking such an \nadventure.";
        dialogues[3] = "Well, I hope you'll have better luck than me.";
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

    public void speak() {

        super.speak();

    }
}
