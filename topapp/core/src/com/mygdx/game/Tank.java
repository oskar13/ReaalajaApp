package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.physics.box2d.World;

public class Tank {
	
    private Texture hullTexture;
    private Sprite hullSprite;
    
    private Texture barrelTexture;
    private Sprite barrelSprite;
    
    private float posX;
    private float posY;
    
    private int fireRate = 10;
    private int fireRateCooldown = 0;
    
    public Tank(int x, int y) {
    	posX = x;
    	posY = y;
   	
    	hullTexture = new Texture(Gdx.files.internal("assets/tankHull.png"));
        hullSprite = new Sprite(hullTexture);
        
        barrelTexture = new Texture(Gdx.files.internal("assets/tankBarrel.png"));
        barrelSprite = new Sprite(barrelTexture);
    }
    
    public void drawTank(SpriteBatch batch) {
    	
    	barrelSprite.setOrigin(0f, barrelSprite.getHeight()/2);
    	
    	barrelSprite.setY(posY+hullSprite.getHeight()/2);
    	barrelSprite.setX(posX+hullSprite.getWidth()/2);
    	
    	
    	
    	/*
        
        ShapeRenderer sr = new ShapeRenderer();
        sr.setColor(Color.BLACK);
        
        sr.begin(ShapeType.Line);
        sr.line(posX+hullSprite.getWidth()/2, posY+hullSprite.getHeight()/2, Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY());

        sr.end();*/
    	
    	
    	
    	barrelSprite.setRotation(getAngle(posX+hullSprite.getWidth()/2, posY+hullSprite.getHeight()/2, Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY()));
    	
    	
    	
    	hullSprite.setX(posX);
    	hullSprite.setY(posY);
    	
    	
    	barrelSprite.draw(batch);
    	hullSprite.draw(batch);
    	
    	
    	
    	if (fireRateCooldown > 0 ) {
    		fireRateCooldown -= 1;
    	} 
    	

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
    
    public void shoot(World world, ArrayList list) {
    	
    	if (fireRateCooldown == 0) {
    			list.add(new Projectile(world, Gdx.input.getX()-posX, Gdx.graphics.getHeight()-Gdx.input.getY()-posY          ,posX+hullSprite.getWidth()/2,posY+hullSprite.getHeight()/2));
    			fireRateCooldown = fireRate;
    	}
    }
    
    
    
    public float getAngle(float x1, float y1, float x2, float y2) {
        float angle = (float) Math.toDegrees(Math.atan2(y2 - y1, x2 - x1));

        if(angle < 0){
            angle += 360;
        }

        return angle;
    }

    
    

}
