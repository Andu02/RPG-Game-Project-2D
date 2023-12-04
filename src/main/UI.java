package main;

import entity.Entity;
import entity.Player;
import object.OBJ_Heart;
import object.OBJ_Mana;

import java.awt.*;
import java.awt.image.AreaAveragingScaleFilter;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font mainFont, cambriaFont;

    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();

    // DIALOGUE
    public String currentDialogue = "";
    public String nameDialogue = "";

    // MENU
    public int commandNum = 0;
    public int titleScreenState = 0;

    // PLAYER STATS
    BufferedImage heart_full, heart_half, heart_blank;
    BufferedImage mana_full, mana_blank;

    // PAUSE SCREEN
    public int pauseCommandNum = 0;

    // INVENTORY
    public int slotCol = 0;
    public int slotRow = 0;

    public UI(GamePanel gp) {
        this.gp = gp;

       try {
           InputStream is = getClass().getResourceAsStream("/fonts/x12y16pxMaruMonica.ttf");
           assert is != null;
           mainFont = Font.createFont(Font.TRUETYPE_FONT, is);
           is = getClass().getResourceAsStream("/fonts/cambriab.ttf");
           assert is != null;
           cambriaFont = Font.createFont(Font.TRUETYPE_FONT, is);
       } catch (Exception e) {
           e.printStackTrace();
       }

       // HUD OBJECT
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;

        Entity mana = new OBJ_Mana(gp);
        mana_full = mana.image;
        mana_blank = mana.image2;

    }

    public void addMessage(String text) {

        message.add(text);
        messageCounter.add(0);

    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;

        // TITLE STATE
        if(gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        // PLAY STATE
        if(gp.gameState == gp.playState){
            // HUD OBJECT
            drawPlayerLife();
            drawPlayerMana();

            //MESSAGE
            drawMessage();
        }
        // PAUSE STATE
        if(gp.gameState == gp.pauseState) {
            drawPlayerLife();
            drawPlayerMana();
            drawPauseScreen();
        }
        // DIALOGUE STATE
        if(gp.gameState == gp.dialogueState) {
            drawPlayerLife();
            drawPlayerMana();
            drawDialogueScreen();
        }
        // CHARACTER STATE
        if(gp.gameState == gp.characterState) {
            drawStatusWindow();
            drawInventoryWindow();
        }
        // GAME OVER STATE
        if(gp.gameState == gp.gameOverState) {
            drawGameOverScreen();
        }

    }

    public void drawPlayerLife() {

        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;

        // MAX LIFE
        while(i < gp.player.maxLife/2) {
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize + 3;
        }

        // RESET
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;

        // DRAW CURRENT LIFE
        while(i < gp.player.life) {
            g2.drawImage(heart_half, x, y, null);
            i++;
            if(i < gp.player.life) {
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.tileSize + 3;
        }


    }

    public void drawPlayerMana() {

        int x = gp.tileSize/2 - 3;
        int y = gp.tileSize/2 + gp.tileSize + 5;
        int i = 0;

        // MAX MANA
        while(i < gp.player.maxMana) {
            g2.drawImage(mana_blank, x, y, null);
            i++;
            x += gp.tileSize - 12;
        }

        // RESET
        x = gp.tileSize/2 - 3;
        y = gp.tileSize/2 + gp.tileSize + 5;
        i = 0;

        // DRAW CURRENT MANA
        while(i < gp.player.mana) {
            g2.drawImage(mana_full, x, y, null);
            i++;
            x += gp.tileSize - 12;
        }

    }

    public void drawTitleScreen() {

        String text;
        int x;
        int y;

        if(titleScreenState == 0) {

            // BACKGROUND
            g2.setColor(new Color(23, 21, 21, 255));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            // TITLE NAME
            g2.setFont(mainFont);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 86F));
            text = "Undo's Adventures 2D";
            x = getXforCenteredText(text);
            y = gp.tileSize * 3;

            // SHADOW
            g2.setColor(Color.lightGray);
            g2.drawString(text, x + 3, y + 3);

            // MAIN COLOR
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            // ANDU IMAGE
            x = gp.screenWidth / 2 - gp.tileSize * 2;
            y += gp.tileSize * 2;
            g2.drawImage(gp.player.down2, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

            // MARA IMAGE
            x = gp.screenWidth / 2;
            g2.drawImage(gp.Mara.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

            // MENU --------------

            // NEW GAME
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 46F));
            text = "NEW GAME";
            x = getXforCenteredText(text);
            y += (int) (gp.tileSize * 3.5);
            g2.drawString(text, x, y);

            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            // LOAD GAME
            text = "LOAD GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);

            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            // QUIT
            text = "QUIT";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);

            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            // CLASS SELECTION
        } else if (titleScreenState == 1) {


            // BACKGROUND
            g2.setColor(new Color(23, 21, 21, 255));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            g2.setFont(mainFont);
            g2.setColor(Color.white);

            // SELECT YOUR CLASS
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 46F));
            text = "Select your class";
            x = getXforCenteredText(text);
            y = gp.tileSize * 3;
            g2.drawString(text, x, y);

            // FIRST CLASS - WARRIOR
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 46F));
            text = "Warrior";
            x = getXforCenteredText(text);
            y += gp.tileSize * 3;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            // SECOND CLASS - SORCERER
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 46F));
            text = "Sorcerer";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }


            // THIRD CLASS - HOBBIT
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 46F));
            text = "Hobbit";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            // BACK
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 46F));
            text = "Back";
            x = getXforCenteredText(text);
            y += gp.tileSize*2;
            g2.drawString(text, x, y);
            if (commandNum == 3) {
                g2.drawString(">", x - gp.tileSize, y);
            }

        }
    }

    public void drawPauseScreen() {

        g2.setFont(mainFont);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); // OPTIMIZATION
        g2.setColor(Color.white);

        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2 - (gp.tileSize);
        g2.drawString(text, x, y);

        // SETTINGS - MUSIC ON/OFF
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 60F));
        if(gp.musicOn) {
            text = "Music ON";
        }
        else {
            text = "Music OFF";
        }
        x = getXforCenteredText(text);
        y = gp.screenHeight / 2 + (int)(gp.tileSize * 1.5);
        g2.drawString(text, x, y);
        if(pauseCommandNum == 0) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        // FULLSCREEN
        if(!gp.fullScreen) {
            text = "Windowed";
        }
        else {
            text = "Fullscreen";
        }
        x = getXforCenteredText(text);
        y += gp.tileSize + 10;
        g2.drawString(text, x, y);
        if(pauseCommandNum == 1) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        // RETURN TO MAIN MENU
        text = "Main Menu";
        x = getXforCenteredText(text);
        y += gp.tileSize + 10;
        g2.drawString(text, x, y);
        if(pauseCommandNum == 2) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        gp.config.saveConfig();

    }

    public void drawDialogueScreen() {

        // WINDOW
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - gp.tileSize * 4;
        int height = gp.tileSize * 4;

        drawSubWindow(x, y, width, height);

        // TEXT
        g2.setFont(mainFont);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); // OPTIMIZATION
        x += gp.tileSize;
        y += gp.tileSize;

        // SPEAKER'S NAME
        if(!nameDialogue.isEmpty()) {
            g2.drawString(nameDialogue, x, y);
            y += 40;
        }

        // DIALOGUE
        for(String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y+= 40;
        }

    }

    public void drawSubWindow (int x, int y, int width, int height) {

        Color c = new Color(0, 0, 0, 220);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);

    }

    public void drawStatusWindow() {

        // WINDOW
        int frameX = gp.tileSize;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 5;
        int frameHeight = gp.tileSize * 10;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // TEXT
        g2.setFont(mainFont);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 35;
        int tailX = (frameX + frameWidth) - 30;

        // STATS
        g2.drawString("Level", textX, textY);
        drawStat(String.valueOf(gp.player.level), tailX, textY);
        textY += lineHeight;

        g2.drawString("Life", textX, textY);
        drawStat(String.valueOf(gp.player.life + "/" + gp.player.maxLife), tailX, textY);
        textY += lineHeight;

        g2.drawString("Strength", textX, textY);
        drawStat(String.valueOf(gp.player.strength), tailX, textY);
        textY += lineHeight;

        g2.drawString("Dexterity", textX, textY);
        drawStat(String.valueOf(gp.player.dexterity), tailX, textY);
        textY += lineHeight;

        g2.drawString("Attack", textX, textY);
        drawStat(String.valueOf(gp.player.attack), tailX, textY);
        textY += lineHeight;

        g2.drawString("Defense", textX, textY);
        drawStat(String.valueOf(gp.player.defense), tailX, textY);
        textY += lineHeight;

        g2.drawString("Experience", textX, textY);
        drawStat(gp.player.exp + "/" + gp.player.nextLevelExp, tailX, textY);
        textY += lineHeight;

        g2.drawString("Coin", textX, textY);
        drawStat(String.valueOf(gp.player.coin), tailX, textY);
        textY += lineHeight + 20;

        g2.drawString("Weapon", textX, textY);
        drawBorder(tailX - gp.tileSize, textY - 30, gp.tileSize, gp.tileSize, 3);
        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY - 30, gp.tileSize, gp.tileSize, null);
        textY += lineHeight + 35;

        g2.drawString("Shield", textX, textY);
        drawBorder(tailX - gp.tileSize, textY - 30, gp.tileSize, gp.tileSize, 3);
        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY - 30, gp.tileSize, gp.tileSize, null);
        textY += lineHeight;

    }

    public void drawStat(String stat, int tailX, int textY) {
        int textX = getXforAlignToRightText(stat, tailX);
        g2.drawString(stat, textX, textY);
    }

    public void drawInventoryWindow() {

        // WINDOW
        int frameX = gp.tileSize * 13;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 6;
        int frameHeight = gp.tileSize * 5;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // SLOT
        final int slotXstart = frameX + 16;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 3;

        // DRAW ITEMS
        for(int i = 0; i < gp.player.inventory.size(); i++) {

            // EQUIP CURSOR
            if(gp.player.inventory.get(i) == gp.player.currentWeapon || gp.player.inventory.get(i) == gp.player.currentShield) {
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }

            if(gp.player.inventory.get(i) != null) {
                g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, gp.tileSize, gp.tileSize, null);

                slotX += slotSize;

                if(i == 4 || i == 9 || i == 14) {
                    slotX = slotXstart;
                    slotY += slotSize;
                }
            }

        }

        // CURSOR
        int cursorX = slotXstart + slotSize * slotCol;
        int cursorY = slotYstart + slotSize * slotRow;
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;

        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        // DESCRIPTION WINDOW
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeight;
        int dFrameWidth = frameWidth;
        int dFrameHeight = gp.tileSize*3;

        int textX = dFrameX + 20;
        int textY = dFrameY + gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(28F));

        int itemIndex = getItemIndexOnSlot();

        if(itemIndex < gp.player.inventory.size()) {

            drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

            for (String line: gp.player.inventory.get(itemIndex).description.split("\n")) {

                g2.drawString(line, textX, textY);
                textY += 32;

            }
        }

    }

    public int getItemIndexOnSlot() {
        return slotCol + (slotRow*5);
    }

    public int getXforCenteredText(String text) {

        int textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();

        return gp.screenWidth/2 - textLength/2;

    }

    public int getXforAlignToRightText(String text, int tailX) {

        int textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();

        return tailX - textLength;

    }

    public void drawBorder(int x, int y, int width, int height, int size) {

        g2.setColor(new Color(42, 39, 39, 130));
        g2.fillRoundRect(x-size*2, y-size*2, width+size*4, height+size*4, 25, 25);
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(size));
        g2.drawRoundRect(x-size*2, y-size*2, width+size*4, height+size*4, 25, 25);

    }

    public void drawMessage() {

        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;

        g2.setFont(mainFont);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 25f));

        for(int i = 0; i < message.size(); i++) {

            if(message.get(i) != null) {

                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX+2, messageY+2);
                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1; // messageCounter++
                messageCounter.set(i, counter);
                messageY += 50;

                if(messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);

                }

            }

        }

    }

    public void drawGameOverScreen() {

        g2.setFont(mainFont);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); // OPTIMIZATION
        g2.setColor(new Color(182, 16, 16));

        String text = "GAME OVER";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2 - (gp.tileSize);
        g2.drawString(text, x, y);

        g2.setColor(Color.white);

        // RETRY
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 60F));
        text = "Retry";
        x = getXforCenteredText(text);
        y = gp.screenHeight / 2 + (int)(gp.tileSize * 1.5);
        g2.drawString(text, x, y);
        if(pauseCommandNum == 0) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        // RESTART
        text = "Restart";
        x = getXforCenteredText(text);
        y += gp.tileSize + 4;
        g2.drawString(text, x, y);
        if(pauseCommandNum == 1) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        // EXIT
        text = "EXIT";
        x = getXforCenteredText(text);
        y += gp.tileSize + 10;
        g2.drawString(text, x, y);
        if(pauseCommandNum == 2) {
            g2.drawString(">", x - gp.tileSize, y);
        }


    }

}
