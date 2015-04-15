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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.graphics.OrthographicCamera;


public class TopApp extends ApplicationAdapter {

	private World world = new World(new Vector2(0, -300), true); 
	private OrthographicCamera camera;
	
    private SpriteBatch batch;
    private Texture texture;
    private Sprite sprite;
    
    private Tank juku;
    
    public static ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
    
    public static ArrayList<Tank> players = new ArrayList<Tank>();
    
    
    
    
    
    
	
    @Override
    public void create() {        
        batch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("assets/tankHull.png"));
        sprite = new Sprite(texture);
        camera = new OrthographicCamera(1280, 720);
        
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
        
        
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        
        //Much main draw loop

        
        
        
        sprite.draw(batch);
        
        juku.drawTank(batch);
        
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){

        	juku.driveForward();
        	camera.translate(1f, 1f);

            camera.update();
        }
        
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){

        	juku.driveBack();
        }
        
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
        	
        	System.out.println("BOOM");
        	
        	//projectiles.add(juku.shoot(world));
        	
        	juku.shoot(world, projectiles);
        	
        	//projectiles.add( new Projectile(world, juku.getX(), 800f , 4.f,1,1));

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
    
    public boolean pan(float x, float y, float deltaX, float deltaY) {

       // TODO Auto-generated method stub

       camera.translate(deltaX,0);

       camera.update();

       return false;

    }
}
