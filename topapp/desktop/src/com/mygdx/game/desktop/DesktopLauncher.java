package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.TopApp;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "Title";
		//config.useGL20 = true;
		//config.fullscreen = true;
		config.height = 1000;
		config.width = 720;	
		
		new LwjglApplication(new TopApp(), config);
		
		
		

	}
}
