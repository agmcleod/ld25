package com.agmcleod.ld25.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.agmcleod.ld25.MyGame;

public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.width = 1024;
        cfg.height = 768;
        cfg.title = "Ludum Dare 25 - You are the villain - by agmcleod";
        new LwjglApplication(new MyGame(), cfg);
    }
}
