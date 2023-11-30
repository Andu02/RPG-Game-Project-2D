package main;

import entity.Entity;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    // TILE COLLISION
    public void checkTile(Entity entity) {

        int entityLeftWorldX = entity.worldX + entity.collisionArea.x;
        int entityRightWorldX = entity.worldX + entity.collisionArea.x + entity.collisionArea.width;
        int entityTopWorldY = entity.worldY + entity.collisionArea.y;
        int entityBottomWorldY = entity.worldY + entity.collisionArea.y + entity.collisionArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1 = -1, tileNum2 = -1;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                break;

        }

        if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
            entity.collisionOn = true;
        }

    }

    // OBJECT COLLISION
    public int checkObject(Entity entity, boolean player) {

        int index = 999;

        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {

                // entity's collision area
                entity.collisionArea.x = entity.worldX + entity.collisionArea.x;
                entity.collisionArea.y = entity.worldY + entity.collisionArea.y;

                // object's collision area
                gp.obj[i].collisionArea.x = gp.obj[i].worldX + gp.obj[i].collisionArea.x;
                gp.obj[i].collisionArea.y = gp.obj[i].worldY + gp.obj[i].collisionArea.y;

                collisionByDirection(entity);

                if (entity.collisionArea.intersects(gp.obj[i].collisionArea)) {
                    if (gp.obj[i].collision) {
                        entity.collisionOn = true;
                    }
                    if (player) {
                        index = i;
                    }
                }

                entity.collisionArea.x = entity.collisionAreaDefaultX;
                entity.collisionArea.y = entity.collisionAreaDefaultY;
                gp.obj[i].collisionArea.x = gp.obj[i].collisionAreaDefaultX;
                gp.obj[i].collisionArea.y = gp.obj[i].collisionAreaDefaultY;
            }
        }
        return index;
    }

    // NPC OR MONSTER COLLISION
    public int checkEntity(Entity entity, Entity[] target) {

        int index = 999;

        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {

                entity.collisionArea.x = entity.worldX + entity.collisionArea.x;
                entity.collisionArea.y = entity.worldY + entity.collisionArea.y;

                target[i].collisionArea.x = target[i].worldX + target[i].collisionArea.x;
                target[i].collisionArea.y = target[i].worldY + target[i].collisionArea.y;

                collisionByDirection(entity);

                if (entity.collisionArea.intersects(target[i].collisionArea) && entity != target[i]) {
                    entity.collisionOn = true;
                    index = i;
                }

                entity.collisionArea.x = entity.collisionAreaDefaultX;
                entity.collisionArea.y = entity.collisionAreaDefaultY;
                target[i].collisionArea.x = target[i].collisionAreaDefaultX;
                target[i].collisionArea.y = target[i].collisionAreaDefaultY;
            }
        }
        return index;

    }

    public boolean checkPlayer(Entity entity) {

        boolean contactPLayer = false;

        entity.collisionArea.x = entity.worldX + entity.collisionArea.x;
        entity.collisionArea.y = entity.worldY + entity.collisionArea.y;

        gp.player.collisionArea.x = gp.player.worldX + gp.player.collisionArea.x;
        gp.player.collisionArea.y = gp.player.worldY + gp.player.collisionArea.y;

        collisionByDirection(entity);

        if (entity.collisionArea.intersects(gp.player.collisionArea)) {
            entity.collisionOn = true;
            contactPLayer = true;
        }

        entity.collisionArea.x = entity.collisionAreaDefaultX;
        entity.collisionArea.y = entity.collisionAreaDefaultY;
        gp.player.collisionArea.x = gp.player.collisionAreaDefaultX;
        gp.player.collisionArea.y = gp.player.collisionAreaDefaultY;

        return contactPLayer;
    }

    // UTILITY
    private void collisionByDirection(Entity entity) {
        switch (entity.direction) {
            case "up": entity.collisionArea.y -= entity.speed; break;
            case "down": entity.collisionArea.y += entity.speed; break;
            case "left": entity.collisionArea.x -= entity.speed; break;
            case "right": entity.collisionArea.x += entity.speed;break;
        }
    }
}




