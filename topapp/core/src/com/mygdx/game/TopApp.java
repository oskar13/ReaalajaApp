package com.mygdx.game;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
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

	final float PIXELS_TO_METERS = 100f;

	Texture texture;
	Texture hitTex;
	Matrix4 debugMatrix;
	Box2DDebugRenderer debugRenderer;
	static OrthographicCamera cam;
	SpriteBatch batch;	
	final Sprite[][] sprites = new Sprite[10][10];
	//final Matrix4 matrix = new Matrix4();	

	World world;
	Sound horn;
	Sound hit;
	private Tank juku;
	Boolean followPlayer = false;
	Body bodyEdgeScreen;
	public static ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	
	public ArrayList<Hitmarker> hitmarkers = new ArrayList<Hitmarker>();
	
	RayHandler rayHandler;
	PointLight light1;
	PointLight light2;


	@Override public void create() {
		



		world = new World(new Vector2(0, -2f),true);

		horn = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/horn.mp3"));
		hit = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/hitmarker.mp3"));

		texture = new Texture(Gdx.files.internal("assets/doge.png"));	
		hitTex = new Texture(Gdx.files.internal("assets/hitmarker.png"));
		debugRenderer = new Box2DDebugRenderer();
		//cam = new OrthographicCamera(10, 10 * (Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()));		
		cam = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		cam.position.set(0, 0, 50);
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



		rayHandler = new RayHandler(world);
		
		RayHandler.useDiffuseLight(true);
		
		light1 = new PointLight(rayHandler, 5000, new Color().PURPLE, 13, -6, 6);
		
		light2 = new PointLight(rayHandler, 5000, new Color(0.8f, 0.4f, 0.2f, 1f), 13, 6, 6);
		
		
		rayHandler.setAmbientLight(0.35f, 0.4f, 0.35f, 1f);



		world.setContactListener(new ContactListener() {
			@Override
			public void beginContact(Contact contact) {
				/*
                // Check to see if the collision is between the second sprite and the bottom of the screen
                // If so apply a random amount of upward force to both objects... just because
                if((contact.getFixtureA().getBody() == bodyEdgeScreen &&
                        contact.getFixtureB().getBody() == body2)
                        ||
                        (contact.getFixtureA().getBody() == body2 &&
                                contact.getFixtureB().getBody() == bodyEdgeScreen)) {

                    body.applyForceToCenter(0,MathUtils.random(20,50),true);
                    body2.applyForceToCenter(0, MathUtils.random(20,50), true);
                }
				 */
				
				
			}

			@Override
			public void endContact(Contact contact) {
			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				
				
				if (impulse.getNormalImpulses()[0] > 3f) {
				System.out.println(impulse.getNormalImpulses()[0]);
				
				Vector2[] contactPoints = contact.getWorldManifold().getPoints();
				//ArrayList<Vector2> asdf = contact.getWorldManifold().getPoints().length;
				
				
				System.out.println(contact.toString());
				
				//for(int i =0; i < contactPoints.length; i++) {
				int i = 0;
					System.out.println("X: "+contactPoints[i].x);
					System.out.println("Y: "+contactPoints[i].y);
					
					Hitmarker newhit = new Hitmarker(hitTex);
					newhit.setOriginCenter();
					newhit.setPosition((contactPoints[i].x*PIXELS_TO_METERS) - newhit.getWidth()/2, (contactPoints[i].y*PIXELS_TO_METERS) - newhit.getHeight()/2);
					hitmarkers.add(newhit);
					
				//}
				
				hit.play(0.5f);
				}
				
				
			}
		});
	}

	@Override public void render() {
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		cam.update();
		// Step the physics simulation forward at a rate of 60hz
		world.step(1f/60f, 6, 2);


		if(Gdx.input.isKeyPressed(Input.Keys.W)){
			cam.rotate(-0.3f);
		}

		if(Gdx.input.isKeyPressed(Input.Keys.S)){
			cam.rotate(0.3f);
		}
		
		
		

		/*
		 * MLG 
		Random rand = new Random();
		int  n = rand.nextInt(20) + 1;
		n= n-(n/2);
		cam.up.set(0, 1, 0);
		cam.rotate(n);
		cam.position.set(n,n,cam.position.z);
		*/
		if (followPlayer) {
			cam.position.set(juku.getX(),juku.getY()+Gdx.graphics.getHeight()/3,cam.position.z);
		}

		if(Gdx.input.isKeyPressed(Input.Keys.Q)){

			cam.zoom += 0.01;
			
			if (cam.zoom > 5f) {
				cam.zoom = 5f;
			}
			
			System.out.println("zoom: " + cam.zoom );

		}

		if(Gdx.input.isKeyPressed(Input.Keys.E)){

			cam.zoom -= 0.01;
			
			if (cam.zoom < 0.2) {
				cam.zoom = 0.2f;
			}

			System.out.println("zoom: " + cam.zoom );

		}




		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			cam.translate(-5,0,0);
		}

		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			cam.translate(5,0,0);
		}

		if(Gdx.input.isKeyPressed(Input.Keys.UP)){
			cam.translate(0,5,0);
		}

		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			cam.translate(0,-5,0);
		}



		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



		// Scale down the sprite batches projection matrix to box2D size
		debugMatrix = batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS, 
				PIXELS_TO_METERS, 0);



		batch.setProjectionMatrix(cam.combined);
		//batch.setTransformMatrix(matrix);
		batch.begin();
		/*
		for(int y = 0; y < 10; y++) {
			for(int x = 0; x < 10; x++) {
				sprites[x][y].draw(batch);
			}
		}
*/
		juku.drawTank(batch);
		
        Iterator<Projectile> it = projectiles.iterator();
        while (it.hasNext()) {
        	Projectile currentProjectile = it.next();
        	
        	currentProjectile.drawProjectile(batch);
        	if (currentProjectile.getPosition()< -80) {
        		it.remove();
        	} 	
        }

        

		batch.end();
		
		

		debugRenderer.render(world, debugMatrix);
		
		
		
		rayHandler.setCombinedMatrix( batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS, 
				PIXELS_TO_METERS, 0));
		rayHandler.updateAndRender();
		
		
		
		batch.begin();
        Iterator<Hitmarker> it2 = hitmarkers.iterator();
        while (it2.hasNext()) {
        	Hitmarker currentHitmarker = it2.next();
        	currentHitmarker.draw(batch);
        	if (currentHitmarker.isKill()) {
				it2.remove();
			} else {
				
			}
        	
        	
        }
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

			juku.shoot(world, projectiles);
			horn.play(0.6f);
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		
		if (keycode == Input.Keys.F) {
			followPlayer = !followPlayer;
		}
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