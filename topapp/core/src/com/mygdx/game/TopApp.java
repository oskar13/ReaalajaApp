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
	int dogex=1;
	int dogey=1;
	Boolean pomf= false;
	

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, Gdx.input.getX() , -Gdx.input.getY());
		batch.draw(img, 0, i);
		batch.draw(img, 0, -i);
		batch.draw(img, dogex, -dogey);
		batch.setColor( new Color(0.4f, 0.4f, 0.4f, 0.4f));
		
		   System.out.println("Normal getX(): " + Gdx.input.getX()); 
		   System.out.println("Normal getY(): " + Gdx.input.getY()); 
		   

		
		if (dogex < Gdx.input.getX()) {
			dogex+=1;
		} else if (dogex > Gdx.input.getX()) {
			dogex-=1;
		}
		
		if (dogey > Gdx.input.getY()) {
			dogey-=1;
		} else if (dogey < Gdx.input.getY()) {
			dogey+=1;
		}
		
		
		if (i>3 || pomf) {
			pomf = true;
			i-=1;
			if (i<0) {
				pomf = false;
			}
		} else {
			i+=1;
		}
		
		batch.end();
	}
}
