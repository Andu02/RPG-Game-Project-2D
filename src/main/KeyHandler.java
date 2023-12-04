package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed;

    // DEBUG
    boolean checkDrawTime = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        // TITLE STATE
        if (gp.gameState == gp.titleState) {
            titleState(code);
        }

        // PLAY STATE
        else if (gp.gameState == gp.playState) {
            playState(code);
        }

        // PAUSE STATE
        else if (gp.gameState == gp.pauseState) {
            pauseState(code);
        }
        // DIALOGUE STATE
        else if (gp.gameState == gp.dialogueState) {
            dialogueState(code);
        }
        // CHARACTER STATE
        else if (gp.gameState == gp.characterState) {
            characterState(code);
        }
        else if (gp.gameState == gp.gameOverState) {
            gameOverState(code);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        switch (code) {
            // MOVEMENT
            case KeyEvent.VK_W:
                upPressed = false;
                break;
            case KeyEvent.VK_S:
                downPressed = false;
                break;
            case KeyEvent.VK_A:
                leftPressed = false;
                break;
            case KeyEvent.VK_D:
                rightPressed = false;
                break;
            // DIALOGUE
            case KeyEvent.VK_ENTER:
                enterPressed = false;
                break;
            case KeyEvent.VK_X:
                shotKeyPressed = false;
                break;
        }
    }

    public void playState(int code) {

        switch (code) {
            // MOVEMENT
            case KeyEvent.VK_W:
                upPressed = true;
                break;
            case KeyEvent.VK_S:
                downPressed = true;
                break;
            case KeyEvent.VK_A:
                leftPressed = true;
                break;
            case KeyEvent.VK_D:
                rightPressed = true;
                break;
            // PAUSE
            case KeyEvent.VK_ESCAPE:
                gp.gameState = gp.pauseState;
                break;
            // DIALOGUE
            case KeyEvent.VK_ENTER:
                enterPressed = true;
                break;
            // CHARACTER
            case KeyEvent.VK_P:
                gp.gameState = gp.characterState;
                break;
            // SHOOTING
            case KeyEvent.VK_X:
                shotKeyPressed = true;
                break;
            // DEBUG
            case KeyEvent.VK_T:
                checkDrawTime = !checkDrawTime;
                break;
        }

    }

    public void pauseState(int code) {

        switch (code) {
            case KeyEvent.VK_W:
                if (gp.ui.pauseCommandNum != 0) {
                    gp.ui.pauseCommandNum--;
                }
                break;
            case KeyEvent.VK_S:
                if (gp.ui.pauseCommandNum != 2) {
                    gp.ui.pauseCommandNum++;
                }
                break;
            case KeyEvent.VK_ESCAPE:
                gp.gameState = gp.playState;
                break;
            case KeyEvent.VK_ENTER:
                switch (gp.ui.pauseCommandNum) {
                    case 0: // MUSIC
                        if (gp.musicOn) {
                            gp.stopMusic();
                            gp.musicOn = false;
                        } else {
                            gp.playMusic(0);
                            gp.musicOn = true;
                        }
                        break;
                    case 1: // FULLSCREEN
                        if (!gp.fullScreen) {
                            gp.setFullScreen();
                            gp.fullScreen = true;
                        } else {
                            gp.setWindowed();
                            gp.fullScreen = false;
                        }
                        break;
                    case 2: // RETURN TO MAIN MENU
                        gp.ui.titleScreenState = 0;
                        gp.gameState = gp.titleState;
                        break;
                }
                break;
        }

    }

    public void titleState(int code) {

        if (gp.ui.titleScreenState == 0) {

            switch (code) {
                case KeyEvent.VK_W:
                    if (gp.ui.commandNum != 0) {
                        gp.ui.commandNum--;
                    }
                    break;
                case KeyEvent.VK_S:
                    if (gp.ui.commandNum != 2) {
                        gp.ui.commandNum++;
                    }
                    break;
                case KeyEvent.VK_ENTER:
                    switch (gp.ui.commandNum) {
                        case 0: // NEW GAME
                            gp.restart();
                            gp.ui.titleScreenState = 1;
                            break;
                        case 1: // LOAD GAME
                            // add later
                            break;
                        case 2: // QUIT
                            System.exit(0);
                            break;
                    }
                    break;
            }

            //CLASS SELECTION
        } else if (gp.ui.titleScreenState == 1) {

            boolean pressed = false;

            switch (code) {
                case KeyEvent.VK_W:
                    if (gp.ui.commandNum != 0) {
                        gp.ui.commandNum--;
                    }
                    break;
                case KeyEvent.VK_S:
                    if (gp.ui.commandNum != 3) {
                        gp.ui.commandNum++;
                    }
                    break;
                case KeyEvent.VK_ENTER:
                    switch (gp.ui.commandNum) {
                        case 0: // FIRST CLASS
                            System.out.println("FIRST CLASS");
                            pressed = true;
                            break;
                        case 1: // SECOND CLASS
                            System.out.println("SECOND CLASS");
                            pressed = true;
                            break;
                        case 2: // THIRD CLASS
                            System.out.println("THIRD CLASS");
                            pressed = true;
                            break;
                        case 3: //back
                            gp.ui.titleScreenState = 0;
                            gp.ui.commandNum = 0;
                            break;
                    }
                    break;
            }

            if (pressed) {
                gp.gameState = gp.playState;

            }
        }

    }

    public void dialogueState(int code) {

        switch (code) {
            case KeyEvent.VK_ENTER:
                gp.gameState = gp.playState;
                break;
        }

    }

    public void characterState(int code) {

        switch (code) {
            case KeyEvent.VK_P, KeyEvent.VK_ESCAPE:
                gp.gameState = gp.playState;
                break;
            case KeyEvent.VK_W:
                if (gp.ui.slotRow != 0) {
                    gp.ui.slotRow--;
                }
                break;
            case KeyEvent.VK_S:
                if (gp.ui.slotRow != 3) {
                    gp.ui.slotRow++;
                }
                break;
            case KeyEvent.VK_A:
                if (gp.ui.slotCol != 0) {
                    gp.ui.slotCol--;
                }
                break;
            case KeyEvent.VK_D:
                if (gp.ui.slotCol != 4) {
                    gp.ui.slotCol++;
                }
                break;
            case KeyEvent.VK_ENTER:
                gp.player.selectItem();
                break;
        }
    }

    public void gameOverState(int code) {

        switch (code) {
            case KeyEvent.VK_W:
                if (gp.ui.pauseCommandNum != 0) {
                    gp.ui.pauseCommandNum--;
                }
                break;
            case KeyEvent.VK_S:
                if (gp.ui.pauseCommandNum != 2) {
                    gp.ui.pauseCommandNum++;
                }
                break;
            case KeyEvent.VK_ESCAPE:
                gp.gameState = gp.playState;
                break;
            case KeyEvent.VK_ENTER:
                switch (gp.ui.pauseCommandNum) {
                    case 0: // RETRY
                        gp.gameState = gp.playState;
                        gp.retry();
                        break;
                    case 1: // RESTART
                        gp.gameState = gp.playState;
                        gp.restart();
                        break;
                    case 2: // RETURN TO MAIN MENU
                        gp.restart();
                        System.exit(0);
                        break;
                }

        }

    }

}