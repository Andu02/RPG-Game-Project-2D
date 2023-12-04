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
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
                break;

        }

        if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
            entity.collisionOn = true;
        }

    }

    // OBJECT COLLISION
    public int checkObject(Entity entity, boolean player) {

        int index = 999;

        for (int i = 0; i < gp.obj[gp.currentMap].length; i++) {
            if (gp.obj[gp.currentMap][i] != null) {

                // entity's collision area
                entity.collisionArea.x = entity.worldX + entity.collisionArea.x;
                entity.collisionArea.y = entity.worldY + entity.collisionArea.y;

                // object's collision area
                gp.obj[gp.currentMap][i].collisionArea.x = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].collisionArea.x;
                gp.obj[gp.currentMap][i].collisionArea.y = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].collisionArea.y;

                collisionByDirection(entity);

                if (entity.collisionArea.intersects(gp.obj[gp.currentMap][i].collisionArea)) {
                    if (gp.obj[gp.currentMap][i].collision) {
                        entity.collisionOn = true;
                    }
                    if (player) {
                        index = i;
                    }
                }

                entity.collisionArea.x = entity.collisionAreaDefaultX;
                entity.collisionArea.y = entity.collisionAreaDefaultY;
                gp.obj[gp.currentMap][i].collisionArea.x = gp.obj[gp.currentMap][i].collisionAreaDefaultX;
                gp.obj[gp.currentMap][i].collisionArea.y = gp.obj[gp.currentMap][i].collisionAreaDefaultY;
            }
        }
        return index;
    }

    // NPC OR MONSTER COLLISION
    public int checkEntity(Entity entity, Entity[][] target) {

        int index = 999;

        for (int i = 0; i < target[gp.currentMap].length; i++) {
            if (target[gp.currentMap][i] != null) {

                entity.collisionArea.x = entity.worldX + entity.collisionArea.x;
                entity.collisionArea.y = entity.worldY + entity.collisionArea.y;

                target[gp.currentMap][i].collisionArea.x = target[gp.currentMap][i].worldX + target[gp.currentMap][i].collisionArea.x;
                target[gp.currentMap][i].collisionArea.y = target[gp.currentMap][i].worldY + target[gp.currentMap][i].collisionArea.y;

                collisionByDirection(entity);

                if (entity.collisionArea.intersects(target[gp.currentMap][i].collisionArea) && entity != target[gp.currentMap][i]) {
                    entity.collisionOn = true;
                    index = i;
                }

                entity.collisionArea.x = entity.collisionAreaDefaultX;
                entity.collisionArea.y = entity.collisionAreaDefaultY;
                target[gp.currentMap][i].collisionArea.x = target[gp.currentMap][i].collisionAreaDefaultX;
                target[gp.currentMap][i].collisionArea.y = target[gp.currentMap][i].collisionAreaDefaultY;
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




