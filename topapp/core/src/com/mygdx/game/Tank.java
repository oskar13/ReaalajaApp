package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Tank {
	
    private Texture hullTexture;
    private Sprite hullSprite;
    
    private float posX;
    private float posY;
    
    public Tank(int x, int y) {
    	posX = x;
    	posY = y;
   	
    	hullTexture = new Texture(Gdx.files.internal("assets/doge.png"));
        hullSprite = new Sprite(hullTexture);
    }
    
    public void drawTank(SpriteBatch batch) {
    	hullSprite.draw(batch);
    	hullSprite.setX(posX);
    }
    
    
    public void driveForward() {
    	posX += 1;
    }
    
    public void driveBack() {
    	posX -= 1;
    }
    
    public float getX() {
    	return hullSprite.getX();
    }
    

    
    

}
