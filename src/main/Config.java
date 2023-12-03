package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class Config {

    GamePanel gp;

    public Config(GamePanel gp){
        this.gp = gp;
    }

    public void saveConfig() {

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));

            // FULLSCREEN
            if(gp.ui.fullScreen) {
                bw.write("On");
            }
            if(!gp.ui.fullScreen) {
                bw.write("Off");
            }

            bw.flush();
            bw.newLine();

            // MUSIC
            if(gp.ui.musicOn) {
                bw.write("On");
            }
            if(!gp.ui.musicOn) {
                bw.write("Off");
            }

            bw.flush();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public  void loadConfig() {

        try {
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));

            String s = br.readLine();

            // FULLSCREEN
            if(s.equals("On")) {
                gp.ui.fullScreen = true;
            }
            if(s.equals("Off")) {
                gp.ui.fullScreen = false;
            }

            s = br.readLine();

            // MUSIC ON
            if(s.equals("On")) {
                gp.ui.musicOn = true;
            }
            if(s.equals("Off")) {
                gp.ui.musicOn = false;
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
