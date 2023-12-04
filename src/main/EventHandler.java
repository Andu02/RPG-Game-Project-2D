package main;

import java.awt.*;

public class EventHandler {

    GamePanel gp;
    Rectangle eventRect;
    int eventRectDefaultX, eventRectDefaultY;


    public EventHandler(GamePanel gp) {
        this.gp = gp;

        eventRect = new Rectangle(23, 23, 1, 1);
        eventRectDefaultX = eventRect.x;
        eventRectDefaultY = eventRect.y;
    }

    public boolean checkEvent() {

        if(hit(34, 17, "up")) {damagePit(gp.dialogueState); return true;}
        if(hit(28, 15, "up")) {healingWater(gp.dialogueState); return true;}
        if(hit(51, 18, "up")) {enterBuilding(); return true;}
        if(hit(51, 19, "down")) {exitBuilding(); return true;}

        return false;
    }

    public boolean hit(int eventCol, int eventRow, String reqDirection) {

        boolean hit = false;

        gp.player.collisionArea.x = gp.player.worldX + gp.player.collisionArea.x;
        gp.player.collisionArea.y = gp.player.worldY + gp.player.collisionArea.y;
        eventRect.x = eventCol * gp.tileSize + eventRect.x;
        eventRect.y = eventRow * gp.tileSize + eventRect.y;

        if(gp.player.collisionArea.intersects(eventRect)) {
            if(gp.player.direction.contentEquals(reqDirection) ||  reqDirection.contentEquals("any")) {
                hit = true;
            }
         }

        gp.player.collisionArea.x = gp.player.collisionAreaDefaultX;
        gp.player.collisionArea.y = gp.player.collisionAreaDefaultY;
        eventRect.x = eventRectDefaultX;
        eventRect.y = eventRectDefaultY;

        return hit;

    }

    public void damagePit(int gameState) {

        gp.gameState = gameState;

        gp.ui.currentDialogue = "You fell into a pit!";
        gp.player.life--;

    }

    public void healingWater(int gameState) {

        if(gp.keyH.enterPressed) {
            gp.gameState = gameState;
            gp.ui.currentDialogue = "You drank holy water!\nYour life has been restored.";
            gp.player.life = gp.player.maxLife;
        }
    }

    public void enterBuilding() {
        if(gp.currentMap != 1) {
            gp.currentMap = 1;
            gp.player.worldX = 51 * gp.tileSize;
            gp.player.worldY = 18 * gp.tileSize;
        }

    }

    public void exitBuilding() {
        if(gp.currentMap != 0) {
            gp.currentMap = 0;
            gp.player.worldX = 51 * gp.tileSize;
            gp.player.worldY = 18 * gp.tileSize;
        }
    }

}
