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
	Boolean pomf= false;
	

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, Gdx.input.getX() , -Gdx.input.getY());
		batch.draw(img, 0, i);
		batch.draw(img, 0, -i);
		batch.draw(img, 445, 0);
		batch.setColor( new Color(0.4f, 0.4f, 0.4f, 0.4f));
		
		   System.out.println("Normal getX(): " + Gdx.input.getX()); // Print: Normal getX(): "SomeNumber" and Unproject X: "SomeNumber"
		   System.out.println("Normal getY(): " + Gdx.input.getY()); // Print: Normal getY(): "SomeNumber" and Unproject Y: "SomeNumber"
		
		
		if (i>10 || pomf) {
			pomf = true;
			i-=1;
			if (i<10) {
				pomf = false;
			}
		} else {
			i+=1;
		}
		
		batch.end();
	}
}
