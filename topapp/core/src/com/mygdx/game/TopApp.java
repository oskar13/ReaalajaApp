package com.mygdx.game;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;


public class TopApp extends ApplicationAdapter {

	private World world = new World(new Vector2(0, -10), true); 
	
	
    private SpriteBatch batch;
    private Texture texture;
    private Sprite sprite;
    
    private Tank juku;
    
    public static ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
    //public static ArrayList<Player> players = new ArrayList<PPlayer>();
    
    
	
    @Override
    public void create() {        
        batch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("assets/doge.png"));
        sprite = new Sprite(texture);
        
        juku = new Tank(59, 23);
        
        
        
        
    }

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
    }

    @Override
    public void render() {        
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        
        //Much main draw loop
        
        
        sprite.draw(batch);
        
        juku.drawTank(batch);
        
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){

        	juku.driveForward();
        }
        
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){

        	juku.driveBack();
        }
        
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
        	
        	System.out.println("BOOM");
        	projectiles.add( new Projectile(world, juku.getX(), 800f , 4.f,1,1));

        }
        
        
        


        Iterator<Projectile> it = projectiles.iterator();
        while (it.hasNext()) {
        	Projectile currentProjectile = it.next();
        	
        	currentProjectile.drawProjectile(batch);
        	if (currentProjectile.getPosition()< -80) {
        		it.remove();
        	}
        	
        	
        }
        
        batch.end();
        
        
        world.step(1/60f, 6, 2);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
