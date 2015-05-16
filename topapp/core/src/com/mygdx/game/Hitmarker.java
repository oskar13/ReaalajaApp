package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Hitmarker extends Sprite {

	
	private int lifeTime = 10;
	public Hitmarker(Texture hitTex) {
		// TODO Auto-generated constructor stub
		
		super(hitTex);
	}
	
	public Boolean isKill() {
		lifeTime -= 1;
		if (lifeTime == 0) {
			return true;
		}
		return false;
	}

}
