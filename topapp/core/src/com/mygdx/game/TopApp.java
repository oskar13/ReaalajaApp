package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class TopApp extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("assets/doge.png");
	}
	int i =1;
	

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.draw(img, 0, 4+i);
		batch.draw(img, 0, 8-i);
		batch.draw(img, 445, 0);
		batch.setColor( new Color(0.4f, 0.4f, 0.4f, 0.4f));
		
		
		
		if (i>4) {
			i-=1;
		} else {
			i+=1;
		}
		
		batch.end();
	}
}
