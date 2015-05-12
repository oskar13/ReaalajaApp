package com.mygdx.game;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.graphics.OrthographicCamera;


public class TopApp  extends ApplicationAdapter implements InputProcessor{

	private SpriteBatch batch;
	World world;
    Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;
    public static OrthographicCamera camera;
    BitmapFont font;
  
    private Tank juku;
    
    public static int Skoor = 0;
    
    public static ArrayList<Projectile> projectiles = new ArrayList<Projectile>();


    boolean drawSprite = true;

    
    
    final float PIXELS_TO_METERS = 100f;
    

    @Override
    public void create() {
    	

    	
    	
    	batch = new SpriteBatch();
    	

        world = new World(new Vector2(0, -1f),true);
        world.setContactListener(new ListenerClass());



        BodyDef bodyDef2 = new BodyDef();
        bodyDef2.type = BodyDef.BodyType.StaticBody;
        float w = Gdx.graphics.getWidth()/PIXELS_TO_METERS;
        // Set the height to just 50 pixels above the bottom of the screen so we can see the edge in the
        // debug renderer
        float h = Gdx.graphics.getHeight()/PIXELS_TO_METERS- 50/PIXELS_TO_METERS;
        //bodyDef2.position.set(0,
//                h-10/PIXELS_TO_METERS);
        bodyDef2.position.set(0,0);
        FixtureDef fixtureDef2 = new FixtureDef();

        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(-w/2+15,-h/2,w/2,-h/2);
        fixtureDef2.shape = edgeShape;

        Body bodyEdgeScreen = world.createBody(bodyDef2);
        bodyEdgeScreen.createFixture(fixtureDef2);
        edgeShape.dispose();
        
           
        

        Gdx.input.setInputProcessor(this);

        debugRenderer = new Box2DDebugRenderer();
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        

        juku = new Tank(world,700, 30);

    }


    @Override
    public void render() {
    	
    	
    	


    	
        camera.update();
        // Step the physics simulation forward at a rate of 60hz
        world.step(1f/60f, 6, 2);



        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        debugMatrix = batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS,
                PIXELS_TO_METERS, 0);
        batch.begin();
        
        
        
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
        	
        	//System.out.println("BOOM");
        	
        	//projectiles.add(juku.shoot(world));
        	
        	juku.shoot(world, projectiles);
        	
        	//projectiles.add( new Projectile(world, juku.getX(), 800f , 4.f,1,1));

        }
        
        
        juku.drawTank(batch);
        Iterator<Projectile> it = projectiles.iterator();
        while (it.hasNext()) {
        	Projectile currentProjectile = it.next();
        	
        	currentProjectile.drawProjectile(batch);
        	if (currentProjectile.getPosition()< -80) {
        		it.remove();
        	}
        	
        	
        }
        
        
        
        



        font.draw(batch,
                "Skoor: ",
                -Gdx.graphics.getWidth()/2,
               Gdx.graphics.getHeight()/2 );
        batch.end();

        debugRenderer.render(world, debugMatrix);

    }

    @Override
    public void dispose() {
        world.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
    	
    	if(keycode == Input.Keys.W) {
    		camera.zoom -= 0.02;
    	}
    	
    	if(keycode == Input.Keys.S) {
    		camera.zoom += 0.02;
    	}
    	
    	if(keycode == Input.Keys.A) {
    		camera.translate(-3, 0, 0);
    	}
    	
    	if(keycode == Input.Keys.D) {
    		camera.translate(3, 0, 0);
    	}


        if(keycode == Input.Keys.RIGHT)
            
        if(keycode == Input.Keys.LEFT)
           

        if(keycode == Input.Keys.UP)
        	
        if(keycode == Input.Keys.DOWN)
           

        // On brackets ( [ ] ) apply torque, either clock or counterclockwise
        if(keycode == Input.Keys.RIGHT_BRACKET)

        if(keycode == Input.Keys.LEFT_BRACKET)


        // Remove the torque using backslash /
        if(keycode == Input.Keys.BACKSLASH)


        // If user hits spacebar, reset everything back to normal
        if(keycode == Input.Keys.SPACE|| keycode == Input.Keys.NUM_2) {
        	/*
            body.setLinearVelocity(0f, 0f);
            body.setAngularVelocity(0f);
            torque = 0f;
            sprite.setPosition(0f,0f);
            body.setTransform(0f,0f,0f);*/
        }

        if(keycode == Input.Keys.COMMA) {
            
        }
        if(keycode == Input.Keys.PERIOD) {
            
        }
        if(keycode == Input.Keys.ESCAPE || keycode == Input.Keys.NUM_1) {
        	
        }
            

		return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }


    // On touch we apply force from the direction of the users touch.
    // This could result in the object "spinning"
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
    
    
    public static Vector2 getWorldMouse(){
    	Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        
        
        return new Vector2(camera.unproject(mousePos).x, Gdx.graphics.getHeight()/2-camera.unproject(mousePos).y);
    	
    }
	    
}
