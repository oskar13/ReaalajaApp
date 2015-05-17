package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.OrthographicCameraExample;
import com.mygdx.game.TopApp;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "Title";
		//config.useGL20 = true;
		//config.fullscreen = true;
		config.height = 720;
		//config.height = 1080;
		config.width = 1280;
		config.resizable = false;
		
		//config.useCPUSynch = false;
		config.vSyncEnabled = true;
		
		
		
		new LwjglApplication(new TopApp(), config);
		
		
		

	}
}
