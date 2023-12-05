package main;

import entity.NPC_Mara;
import entity.NPC_OldMan;
import monster.MON_BlueSlime;
import object.*;

public class AssetManager {

    GamePanel gp;

    public AssetManager(GamePanel gp) {

        this.gp = gp;

    }

    public void setObject() {

        // MAP 0;
        int mapNum = 0;
        int i = 0;
        gp.obj[mapNum][i] = new OBJ_Door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*28;
        gp.obj[mapNum][i].worldY = gp.tileSize*33;
        i++;

        gp.obj[mapNum][i] = new OBJ_Well(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*28;
        gp.obj[mapNum][i].worldY = gp.tileSize*13;
        i++;

        gp.obj[mapNum][i] = new OBJ_BronzeCoin(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*28;
        gp.obj[mapNum][i].worldY = gp.tileSize*20;
        i++;

        gp.obj[mapNum][i] = new OBJ_Axe_Normal(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*34;
        gp.obj[mapNum][i].worldY = gp.tileSize*18;
        i++;

        gp.obj[mapNum][i] = new OBJ_HealthPotion(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*34;
        gp.obj[mapNum][i].worldY = gp.tileSize*22;
        i++;

        gp.obj[mapNum][i] = new OBJ_ManaPotion(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*36;
        gp.obj[mapNum][i].worldY = gp.tileSize*22;
        i++;

        gp.obj[mapNum][i] = new OBJ_Key(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*28;
        gp.obj[mapNum][i].worldY = gp.tileSize*26;
        i++;

        gp.obj[mapNum][i] = new OBJ_Hole(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*34;
        gp.obj[mapNum][i].worldY = gp.tileSize*17;
        i++;

        // DELETE DROPS
        for(int j = i; j < 20; j++) {
            gp.obj[gp.currentMap][j] = null;
        }


    }

    public void setNPC() {

        // MAP 0
        int mapNum = 0;
        int i = 0;
        gp.npc[mapNum][i] = new NPC_OldMan(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize*30;
        gp.npc[mapNum][i].worldY = gp.tileSize*22;
        i++;

        gp.npc[mapNum][i] = new NPC_Mara(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize*26;
        gp.npc[mapNum][i].worldY = gp.tileSize*22;
        i++;
    }

    public void setMonster() {

        // MAP 0
        int mapNum = 0;
        int i = 0;
        gp.monsters[mapNum][i] = new MON_BlueSlime(gp);
        gp.monsters[mapNum][i].worldX = gp.tileSize*29;
        gp.monsters[mapNum][i].worldY = gp.tileSize*20;
        i++;

        gp.monsters[mapNum][i] = new MON_BlueSlime(gp);
        gp.monsters[mapNum][i].worldX = gp.tileSize*27;
        gp.monsters[mapNum][i].worldY = gp.tileSize*20;
        i++;
    }


}
