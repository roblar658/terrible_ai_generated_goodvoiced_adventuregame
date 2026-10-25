package com.ravnoy.adventure;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

/**
 * Desktop application entry point (LWJGL3).
 */
public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("The Mystery of Raven Island: Secret of the Keeper");
        config.setWindowedMode(1280, 720);
        config.useVsync(true);
        config.setForegroundFPS(60);
        config.setWindowSizeLimits(960, 540, 3840, 2160);

        new Lwjgl3Application(new RavnoyGame(), config);
    }
}
