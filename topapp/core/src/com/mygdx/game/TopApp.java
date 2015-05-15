package com.mygdx.game;
import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.box2d.*;

public class TopApp extends ApplicationAdapter implements InputProcessor {	
	Texture texture;
	static OrthographicCamera cam;
	SpriteBatch batch;	
	final Sprite[][] sprites = new Sprite[10][10];
	//final Matrix4 matrix = new Matrix4();	
	
	World world;
	private Tank juku;
	public static ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

	
	@Override public void create() {
		
		
		world = new World(new Vector2(0, -1f),true);
        world.setContactListener(new ListenerClass());
		
		
		texture = new Texture(Gdx.files.internal("assets/doge.png"));		
		cam = new OrthographicCamera(10, 10 * (Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));			
		cam.position.set(5, 5, 10);
		cam.direction.set(0, 0, -1);
		cam.near = 1;
		cam.far = 100;		
		//matrix.setToRotation(new Vector3(1, 0, 0), 90);
		
		for(int y = 0; y < 10; y++) {
			for(int x = 0; x < 10; x++) {
				sprites[x][y] = new Sprite(texture);
				sprites[x][y].setPosition(x,y);
				sprites[x][y].setSize(1, 1);
			}
		}
		
		batch = new SpriteBatch();
		
		Gdx.input.setInputProcessor(this);
		
		juku = new Tank(world, 59, 23);
	}
	
	@Override public void render() {
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	cam.update();		
	
	
    if(Gdx.input.isKeyPressed(Input.Keys.W)){
    	
    	cam.translate(0, 0.1f, 0);

    }
    
    if(Gdx.input.isKeyPressed(Input.Keys.S)){
    	
    	cam.rotate(0.3f);

    }
    

    if(Gdx.input.isKeyPressed(Input.Keys.Q)){
    	
    	cam.zoom += 0.1;

    }
    
    if(Gdx.input.isKeyPressed(Input.Keys.E)){
    	
    	cam.zoom -= 0.1;

    }
    
    
    
    
    
    if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
    	
    	cam.direction.x -= 0.01;
    	System.out.println("Cam direction x: " + cam.direction.x);
    }
    
    if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
    	
    	cam.direction.x += 0.01;
    	System.out.println("Cam direction x: " + cam.direction.x);
    }
    
    if(Gdx.input.isKeyPressed(Input.Keys.UP)){
    	
    	cam.direction.y += 0.01;
    	System.out.println("Cam direction y: " + cam.direction.y);

    }
    
    if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
    	
    	cam.direction.y -= 0.01;
    	System.out.println("Cam direction y: " + cam.direction.y);

    }
    
    
    
    
    
	
			
	batch.setProjectionMatrix(cam.combined);
	//batch.setTransformMatrix(matrix);
	batch.begin();
	for(int y = 0; y < 10; y++) {
		for(int x = 0; x < 10; x++) {
			sprites[x][y].draw(batch);
		}
	}
	
	juku.drawTank(batch);
	
	batch.end();
	
	checkTileTouched();
}
	
	
	
	final static Plane xyPlane = new Plane(new Vector3(0, 0, 1), 0);
	final static Vector3 intersection = new Vector3();
	Sprite lastSelectedTile = null;
	
	private void checkTileTouched() {
		if(Gdx.input.justTouched()) {
			Ray pickRay = cam.getPickRay(Gdx.input.getX(), Gdx.input.getY());
			Intersector.intersectRayPlane(pickRay, xyPlane, intersection);
			int x = (int)intersection.x;
			int y = (int)intersection.y;
			
			System.out.println("Intersection: x: " +x + " y: " + y);
			if(x >= 0 && x < 10 && y >= 0 && y < 10) {
				if(lastSelectedTile != null) lastSelectedTile.setColor(1, 1, 1, 1);
				Sprite sprite = sprites[x][y];
				sprite.setColor(1, 0, 0, 1);
				lastSelectedTile = sprite;
			}
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
    public static  Vector2 getWorldMouse(){
    	Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
    	
    	
    	Ray pickRay = cam.getPickRay(Gdx.input.getX(), Gdx.input.getY());
		Intersector.intersectRayPlane(pickRay, xyPlane, intersection);
		float x = intersection.x;
		float y = intersection.y;
        
        return new Vector2(x, y);
    	
    }
	
	
}