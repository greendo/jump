package com.jump.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jump.game.Jumper;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = Jumper.HEIGHT;
		config.width = Jumper.WIDTH;
		config.title = "Jumper";
		new LwjglApplication(new Jumper(), config);
	}
}
