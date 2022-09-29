package ru.maxol;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Sonic Loredan Edition");
		config.setWindowedMode(800,600);
//		config.setWindowSizeLimits(637, 358, 637, 358);
		new Lwjgl3Application(new StartGame(), config);
	}
}
