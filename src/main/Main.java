package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Main {

    public static JFrame window;

    public static void main(String[] args) {

        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D Undo's Adventures");

        BufferedImage icon = null;
        try {
            icon = ImageIO.read(Objects.requireNonNull(Main.class.getResourceAsStream("/objects/chest.png")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        window.setIconImage(icon);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        gamePanel.config.loadConfig();
        if(gamePanel.ui.fullScreen) {
            window.setUndecorated(true);
        }

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();

        gamePanel.startGameThread();

    }
    }
