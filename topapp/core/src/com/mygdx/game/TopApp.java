package com.mygdx.game;

import java.util.ArrayList;
import java.util.Iterator;

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


public class TopApp  extends ApplicationAdapter implements InputProcessor {/*

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

    }*/
    SpriteBatch batch;
    Sprite sprite;
    Texture img;
    World world;
    Body body;
    Body bodyEdgeScreen;
    Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;
    public static OrthographicCamera camera;
    BitmapFont font;
    
    private Tank juku;
    
    public static ArrayList<Projectile> projectiles = new ArrayList<Projectile>();


    float torque = 0.0f;
    boolean drawSprite = true;

    final float PIXELS_TO_METERS = 100f;

    @Override
    public void create() {

        batch = new SpriteBatch();
        img = new Texture("assets/doge.png");
        sprite = new Sprite(img);

        sprite.setPosition(-sprite.getWidth()/2,-sprite.getHeight()/2);

        world = new World(new Vector2(0, -1f),true);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((sprite.getX() + sprite.getWidth()/2) /
                        PIXELS_TO_METERS,
                (sprite.getY() + sprite.getHeight()/2) / PIXELS_TO_METERS);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sprite.getWidth()/2 / PIXELS_TO_METERS, sprite.getHeight()
                /2 / PIXELS_TO_METERS);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;
        fixtureDef.restitution = 0.2f;

        body.createFixture(fixtureDef);
        shape.dispose();

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
        edgeShape.set(-w/2,-h/2,w/2,-h/2);
        fixtureDef2.shape = edgeShape;

        bodyEdgeScreen = world.createBody(bodyDef2);
        bodyEdgeScreen.createFixture(fixtureDef2);
        edgeShape.dispose();

        Gdx.input.setInputProcessor(this);

        debugRenderer = new Box2DDebugRenderer();
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.
                getHeight());
        
        
        
        juku = new Tank(world,50, 30);
    }

    private float elapsed = 0;
    @Override
    public void render() {
        camera.update();
        // Step the physics simulation forward at a rate of 60hz
        world.step(1f/60f, 6, 2);

        body.applyTorque(torque,true);

        sprite.setPosition((body.getPosition().x * PIXELS_TO_METERS) - sprite.
                        getWidth()/2 ,
                (body.getPosition().y * PIXELS_TO_METERS) -sprite.getHeight()/2 )
        ;
        sprite.setRotation((float)Math.toDegrees(body.getAngle()));

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        debugMatrix = batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS,
                PIXELS_TO_METERS, 0);
        batch.begin();
        
        
        
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
        	
        	System.out.println("BOOM");
        	
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
        
        
        
        

        if(drawSprite)
            batch.draw(sprite, sprite.getX(), sprite.getY(),sprite.getOriginX(),
                    sprite.getOriginY(),
                    sprite.getWidth(),sprite.getHeight(),sprite.getScaleX(),sprite.
                            getScaleY(),sprite.getRotation());

        font.draw(batch,
                "Restitution: " + body.getFixtureList().first().getRestitution(),
                -Gdx.graphics.getWidth()/2,
               Gdx.graphics.getHeight()/2 );
        batch.end();

        debugRenderer.render(world, debugMatrix);
    }

    @Override
    public void dispose() {
        img.dispose();
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
            body.setLinearVelocity(1f, 0f);
        if(keycode == Input.Keys.LEFT)
            body.setLinearVelocity(-1f,0f);

        if(keycode == Input.Keys.UP)
            body.applyForceToCenter(0f,10f,true);
        	
        if(keycode == Input.Keys.DOWN)
            body.applyForceToCenter(0f, -10f, true);

        // On brackets ( [ ] ) apply torque, either clock or counterclockwise
        if(keycode == Input.Keys.RIGHT_BRACKET)
            torque += 0.1f;
        if(keycode == Input.Keys.LEFT_BRACKET)
            torque -= 0.1f;

        // Remove the torque using backslash /
        if(keycode == Input.Keys.BACKSLASH)
            torque = 0.0f;

        // If user hits spacebar, reset everything back to normal
        if(keycode == Input.Keys.SPACE|| keycode == Input.Keys.NUM_2) {
            body.setLinearVelocity(0f, 0f);
            body.setAngularVelocity(0f);
            torque = 0f;
            sprite.setPosition(0f,0f);
            body.setTransform(0f,0f,0f);
        }

        if(keycode == Input.Keys.COMMA) {
            body.getFixtureList().first().setRestitution(body.getFixtureList().first().getRestitution()-0.1f);
        }
        if(keycode == Input.Keys.PERIOD) {
            body.getFixtureList().first().setRestitution(body.getFixtureList().first().getRestitution()+0.1f);
        }
        if(keycode == Input.Keys.ESCAPE || keycode == Input.Keys.NUM_1)
            drawSprite = !drawSprite;

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
        body.applyForce(1f,1f,screenX,screenY,true);
        //body.applyTorque(0.4f,true);
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
