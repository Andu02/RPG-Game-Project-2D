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

        int i = 0;
        gp.obj[i] = new OBJ_Door(gp);
        gp.obj[i].worldX = gp.tileSize*28;
        gp.obj[i].worldY = gp.tileSize*33;
        i++;

        gp.obj[i] = new OBJ_Well(gp);
        gp.obj[i].worldX = gp.tileSize*28;
        gp.obj[i].worldY = gp.tileSize*13;
        i++;

        gp.obj[i] = new OBJ_BronzeCoin(gp);
        gp.obj[i].worldX = gp.tileSize*28;
        gp.obj[i].worldY = gp.tileSize*20;
        i++;

        gp.obj[i] = new OBJ_Axe_Normal(gp);
        gp.obj[i].worldX = gp.tileSize*34;
        gp.obj[i].worldY = gp.tileSize*18;
        i++;

        gp.obj[i] = new OBJ_HealthPotion(gp);
        gp.obj[i].worldX = gp.tileSize*34;
        gp.obj[i].worldY = gp.tileSize*22;
        i++;

        gp.obj[i] = new OBJ_ManaPotion(gp);
        gp.obj[i].worldX = gp.tileSize*36;
        gp.obj[i].worldY = gp.tileSize*22;
        i++;

        gp.obj[i] = new OBJ_Key(gp);
        gp.obj[i].worldX = gp.tileSize*28;
        gp.obj[i].worldY = gp.tileSize*26;
        i++;

        // DELETE DROPS
        for(int j = i; j < 20; j++) {
            gp.obj[j] = null;
        }


    }

    public void setNPC() {

        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize*30;
        gp.npc[0].worldY = gp.tileSize*22;

        gp.npc[1] = new NPC_Mara(gp);
        gp.npc[1].worldX = gp.tileSize*26;
        gp.npc[1].worldY = gp.tileSize*22;
    }

    public void setMonster() {

        gp.monsters[0] = new MON_BlueSlime(gp);
        gp.monsters[0].worldX = gp.tileSize*29;
        gp.monsters[0].worldY = gp.tileSize*20;

        gp.monsters[1] = new MON_BlueSlime(gp);
        gp.monsters[1].worldX = gp.tileSize*27;
        gp.monsters[1].worldY = gp.tileSize*20;

    }


}
