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
//import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
//import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Achievement.AchievementType;

public class TopApp extends ApplicationAdapter implements InputProcessor {	

	final static float PIXELS_TO_METERS = 100f;

	Texture texture;
	Texture hitTex;
	Texture hitTexBig;
	Texture platformTexture;
	Matrix4 debugMatrix;
	Box2DDebugRenderer debugRenderer;
	static OrthographicCamera cam;
	SpriteBatch batch;	
	SpriteBatch HUDBatch;
	final Sprite[][] sprites = new Sprite[10][10];
	//final Matrix4 matrix = new Matrix4();	
	Sprite failMessage;
	Sprite platformSprite;

	World world;
	Sound horn;
	Sound hit;
	Sound achievementNotifiaction, triple;
	Sound sad, failed;
	private Tank juku;
	Boolean followPlayer = false;
	Boolean MLGcam= false;
	Boolean failedSanic = false;
	Body bodyEdgeScreen;
	public static ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	
	public ArrayList<Hitmarker> hitmarkers = new ArrayList<Hitmarker>();
	public ArrayList<AnimationFx> fxs = new ArrayList<AnimationFx>();
	
	public ArrayList<Sanic> sanics = new ArrayList<Sanic>();
	
	public ArrayList<Achievement> achievements = new ArrayList<Achievement>();
	
	RayHandler rayHandler;
	PointLight light1;
	PointLight light2;
	
	public int killCount = 0;
	
	int sonicCooldown = 0;
	int shotsFired = 0;

	int fiveMinuteAchievement = 5*60*60;
	
	Boolean runOnce = true;
	Boolean accuracyAchievement = false;
	
	public static float posX;
	public static float posY;
	
	public static BitmapFont font1;
	



	@Override public void create() {
		
		
		
		/*FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/fonts/runescape.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();*/
		//parameter.size = 25;
		
		//font1 = new BitmapFont(Gdx.files.internal("assets/fonts/font.fnt"), false);//100 is the font name you can give your font any name
		
		//font1 = generator.generateFont(parameter); // font size 12 pixels
		//generator.dispose(); // don't forget to dispose to avoid memory leaks!

		


		world = new World(new Vector2(0, -2f),true);

		horn = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/horn.mp3"));
		hit = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/hitmarker.mp3"));
		sad = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/sad.mp3"));
		failed = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/failed.mp3"));
		achievementNotifiaction = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/achievement.mp3"));
		triple = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/triple.mp3"));

		font1 = new BitmapFont(Gdx.files.internal("assets/fonts/font.fnt"), false);//100 is the font name you can give your font any name

		
		
		
		texture = new Texture(Gdx.files.internal("assets/fail.png"));	
		hitTex = new Texture(Gdx.files.internal("assets/hitmarker.png"));
		hitTexBig = new Texture(Gdx.files.internal("assets/hitmarkerBig.png"));
		platformTexture = new Texture(Gdx.files.internal("assets/platform.png"));
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
		
		failMessage = new Sprite(texture);
		failMessage.setOriginCenter();
		failMessage.setPosition(-failMessage.getWidth()/2 , -failMessage.getHeight()/2 );
		
		
		

		batch = new SpriteBatch();
		HUDBatch = new SpriteBatch();

		Gdx.input.setInputProcessor(this);

		juku = new Tank(world, 3, 0);
		
		
		

		platformSprite = new Sprite(platformTexture);


        BodyDef bodyDef2 = new BodyDef();
        bodyDef2.type = BodyDef.BodyType.StaticBody;
        
        // Set the height to just 50 pixels above the bottom of the screen so we can see the edge in the
        // debug renderer
        //float h = Gdx.graphics.getHeight()/PIXELS_TO_METERS- 50/PIXELS_TO_METERS;
        //bodyDef2.position.set(0,
//                h-10/PIXELS_TO_METERS);
        bodyDef2.position.set(0,0);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(platformSprite.getWidth()/2 / PIXELS_TO_METERS, platformSprite.getHeight()/2 / PIXELS_TO_METERS);
        
        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 10f; 
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.2f; // Make it bounce a little bit

        bodyDef2.position.set(0,0-((Gdx.graphics.getHeight()/PIXELS_TO_METERS)-( 50/PIXELS_TO_METERS))/2);

		bodyEdgeScreen = world.createBody(bodyDef2);
		bodyEdgeScreen.setUserData(420);
		bodyEdgeScreen.createFixture(fixtureDef);


		
		
		





		rayHandler = new RayHandler(world);
		
		RayHandler.useDiffuseLight(true);
		
		light1 = new PointLight(rayHandler, 5000, new Color().PURPLE, 13, -6, 6);
		
		light2 = new PointLight(rayHandler, 5000, new Color(0.8f, 0.4f, 0.2f, 1f), 13, 6, 6);
		
		PointLight light3 = new PointLight(rayHandler, 5000, new Color(0.8f, 0.8f, 0.2f, 1f), 13, 15, -6);
		
		
		rayHandler.setAmbientLight(0.35f, 0.4f, 0.35f, 1f);
		//rayHandler.setAmbientLight(0.25f, 0.30f, 0.25f, 1f);
		
		
		
		
		

		

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
				if ((contact.getFixtureA().getBody().getUserData().equals(666) && contact.getFixtureB().getBody().getUserData().equals(7))||
						(contact.getFixtureB().getBody().getUserData().equals(666) && contact.getFixtureA().getBody().getUserData().equals(7))){
					System.out.println("REKT");
					Vector2[] contactPoints = contact.getWorldManifold().getPoints();
					fxs.add(new AnimationFx("assets/explosion3.png", 4, 4,contactPoints[0].x,contactPoints[0].y));
					
					Hitmarker newhit = new Hitmarker(hitTexBig);
					newhit.setPosition((contactPoints[0].x*PIXELS_TO_METERS) - newhit.getWidth()/2, (contactPoints[0].y*PIXELS_TO_METERS)- newhit.getHeight()/2);
					hitmarkers.add(newhit);
					
					if (contact.getFixtureA().getBody().getUserData().equals(666)) {
						contact.getFixtureA().getBody().setUserData(999);
					} else {
						contact.getFixtureB().getBody().setUserData(999);
					}

					killCount +=1;
					
					if (killCount == 3) {
						achievements.add(new Achievement(Achievement.AchievementType.TRIPLE));
						achievementNotifiaction.play(1f);
						triple.play(1f);
					}else if (killCount == 12) {
						achievements.add(new Achievement(Achievement.AchievementType.MOM));
						achievementNotifiaction.play(1f);
					}else if (killCount == 100) {
						achievements.add(new Achievement(Achievement.AchievementType.SENPAI));
						achievementNotifiaction.play(1f);
					}
					
					hit.play(1f);
					
				} 
				
				if (!failedSanic) {
				
					if ((contact.getFixtureA().getBody().getUserData().equals(666) && contact.getFixtureB().getBody().getUserData().equals(8))||
							(contact.getFixtureB().getBody().getUserData().equals(666) && contact.getFixtureA().getBody().getUserData().equals(8))){
						MLGcam = true;
						failedSanic = true;
						
						fxs.add(new AnimationFx("assets/explosion1Big.png", 20, 1,posX,posY,0.04f));
						
						achievements.add(new Achievement(Achievement.AchievementType.HEADPHONES));
						
						for (int i = 0; i < 20; i++) {
							Hitmarker newhit = new Hitmarker(hitTexBig);

							newhit.setOriginCenter();
							newhit.setPosition(posX*PIXELS_TO_METERS+randInt(-100, 100),posY*PIXELS_TO_METERS+randInt(-100, 100));
							hitmarkers.add(newhit);
						}
						hit.play(1f);
						sad.play(0.8f);
						failed.play(1f);
						
					}
				}
				
				

				
				
				
				
				
				
				
				
				
				
				if ((contact.getFixtureA().getBody().getUserData().equals(420) && contact.getFixtureB().getBody().getUserData().equals(7))||
						(contact.getFixtureB().getBody().getUserData().equals(420) && contact.getFixtureA().getBody().getUserData().equals(7))){

					
					if (contact.getFixtureA().getBody().getUserData().equals(7)) {
						contact.getFixtureA().getBody().setUserData(420);
					} else {
						contact.getFixtureB().getBody().setUserData(420);
					}
					
				}
				
				
				
				
				
				
				
				
				
				
			}

			@Override
			public void endContact(Contact contact) {
			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				
				
				if (impulse.getNormalImpulses()[0] > 9f) {
					//System.out.println(impulse.getNormalImpulses()[0]);
					
					Vector2[] contactPoints = contact.getWorldManifold().getPoints();
					//ArrayList<Vector2> asdf = contact.getWorldManifold().getPoints().length;
					
					
					System.out.println(contact.toString());

					int i = 0;
						Hitmarker newhit = new Hitmarker(hitTexBig);
						newhit.setOriginCenter();
						newhit.setPosition((contactPoints[i].x*PIXELS_TO_METERS) - newhit.getWidth()/2, (contactPoints[i].y*PIXELS_TO_METERS) - newhit.getHeight()/2);
						hitmarkers.add(newhit);
						
						fxs.add(new AnimationFx("assets/explosion2.png", 10, 4,contactPoints[i].x,contactPoints[i].y));
						
						if (impulse.getNormalImpulses()[0] > 20f) {
							fxs.add(new AnimationFx("assets/explosion1Big.png", 20, 1,contactPoints[i].x,contactPoints[i].y+0.4f,0.04f));
						}


					
					hit.play(0.5f);
				} else if (impulse.getNormalImpulses()[0] > 3f){
					//System.out.println(impulse.getNormalImpulses()[0]);
					
					Vector2[] contactPoints = contact.getWorldManifold().getPoints();
						
					//for(int i =0; i < contactPoints.length; i++) {
					int i = 0;
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
		
		
		posX = juku.getX();
		posY = juku.getY();
		
		
		if (sonicCooldown < 0) {
			
			sonicCooldown = randInt(60, 400);
			sanics.add(new Sanic(world, randInt(-5, -1), randInt(1, 5)));
		}
		sonicCooldown -= 1;
		
		

		
		if (runOnce) {
			achievements.add(new Achievement(Achievement.AchievementType.CLIPPY));
			runOnce = false;
		}
		
		if (fiveMinuteAchievement == 1) {
			achievements.add(new Achievement(Achievement.AchievementType.TOOLONG));
			achievementNotifiaction.play();
			fiveMinuteAchievement -=1;
		} else if(fiveMinuteAchievement > 0){
			fiveMinuteAchievement -=1;
		}
		
		if (!accuracyAchievement) {
			if (shotsFired>30) {
				if((killCount/shotsFired)<0.3f){
					achievements.add(new Achievement(Achievement.AchievementType.ACCURACY));
					achievementNotifiaction.play();
					accuracyAchievement = true;
				}
			}
		}
		
		

/*
		if(Gdx.input.isKeyPressed(Input.Keys.W)){
			cam.rotate(-0.3f);
		}

		if(Gdx.input.isKeyPressed(Input.Keys.S)){
			cam.rotate(0.3f);
		}
		*/
		
		

		/*
		 * MLG */
		if (MLGcam) {
			Random rand = new Random();
			int  n = randInt(0, 20);
			n= n-(n/2);
			cam.up.set(0, 1, 0);
			cam.rotate(n);
			cam.position.set(randInt(0, 20),randInt(0, 20),cam.position.z);
		}
		
		if (followPlayer) {
			cam.position.set(juku.getX()*PIXELS_TO_METERS,juku.getY()*PIXELS_TO_METERS+Gdx.graphics.getHeight()/3,cam.position.z);
		}

		/*if(Gdx.input.isKeyPressed(Input.Keys.Q)){

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

		}*/
		
		if(Gdx.input.isKeyPressed(Input.Keys.A)){

			juku.driveLeft();

		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.D)){

			juku.driveRight();

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


		
		batch.setProjectionMatrix(cam.combined);

		// Scale down the sprite batches projection matrix to box2D size
		debugMatrix = batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS, PIXELS_TO_METERS, 0);



		
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
		
		
		
		platformSprite.setPosition((bodyEdgeScreen.getPosition().x * TopApp.PIXELS_TO_METERS) - platformSprite.getWidth()/2 ,
    	        (bodyEdgeScreen.getPosition().y * TopApp.PIXELS_TO_METERS) -platformSprite.getHeight()/2 );
		platformSprite.draw(batch);
		
		
		
        Iterator<Projectile> it = projectiles.iterator();
        while (it.hasNext()) {
        	Projectile currentProjectile = it.next();
        	
        	currentProjectile.drawProjectile(batch);
        	if (currentProjectile.getPosition()< -8f || currentProjectile.isKill) {
        		world.destroyBody(currentProjectile.body);
        		currentProjectile.body.setUserData(null);
        		currentProjectile.body = null;
        		it.remove();
        	} 	
        }

        
        
        Iterator<Sanic> sanicIt = sanics.iterator();
        while (sanicIt.hasNext()) {
        	Sanic currentSanic = sanicIt.next();
        	
        	currentSanic.render(batch);
        	
        	if (currentSanic.isKill || currentSanic.getY()<-8f) {
        		world.destroyBody(currentSanic.body);
        		currentSanic.body.setUserData(null);
        		currentSanic.body = null;
        		sanicIt.remove();
        	} 	

        }


        

		batch.end();
		

		
		rayHandler.setCombinedMatrix( batch.getProjectionMatrix().cpy().scale(PIXELS_TO_METERS, 
				PIXELS_TO_METERS, 0));
		rayHandler.updateAndRender();
		
		//Stuff not to be lit
		
		batch.begin();
		
        Iterator<AnimationFx> fxit = fxs.iterator();
        while (fxit.hasNext()) {
        	AnimationFx currentFx = fxit.next();
        	currentFx.draw(batch);
        	if (currentFx.isKill()) {
				fxit.remove();
			} else {
				
			}
        }
		
		
        Iterator<Hitmarker> it2 = hitmarkers.iterator();
        while (it2.hasNext()) {
        	Hitmarker currentHitmarker = it2.next();
        	currentHitmarker.draw(batch);
        	if (currentHitmarker.isKill()) {
				it2.remove();
			} else {
				
			}
        }
        
        
        Iterator<Sanic> sanicNameIt = sanics.iterator();
        while (sanicNameIt.hasNext()) {
        	Sanic currentSanic = sanicNameIt.next();
        	currentSanic.renderName(batch, font1);
        }
        
        
        if (failedSanic) {

			int  r = (int) (Math.random()+0.5);
			int  g = (int) (Math.random()+0.5);
			int  b = (int) (Math.random()+0.5);
			
        	failMessage.setColor(r, g, b, 1f);
			failMessage.draw(batch);
		}
        
        

        font1.setColor(new Color().YELLOW);
        font1.draw(batch, "Kill count:" + killCount, cam.position.x- Gdx.graphics.getWidth()/2+20, cam.position.y+Gdx.graphics.getHeight()/4);
        
        
        Iterator<Achievement> achit = achievements.iterator();
        while (achit.hasNext()) {
        	Achievement currentAchievement= achit.next();
        	currentAchievement.render(batch);
        	if (currentAchievement.isKill) {
        		achit.remove();
			} else {
				
			}
        }


        
		batch.end();
		
		//debugRenderer.render(world, debugMatrix);
		



        


		
		

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
			shotsFired +=1;
			if (shotsFired == 420) {
				achievements.add(new Achievement(Achievement.AchievementType.BLAZEIT));
				achievementNotifiaction.play(1f);
			}
			horn.play(0.6f);
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		
		if (keycode == Input.Keys.F) {
			followPlayer = !followPlayer;
		}
		
		if (keycode == Input.Keys.M) {
			MLGcam = !MLGcam;
		}
		
		if (keycode == Input.Keys.U) {

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
	
	
	/**
	 * Returns a psuedo-random number between min and max, inclusive.
	 * The difference between min and max can be at most
	 * <code>Integer.MAX_VALUE - 1</code>.
	 *
	 * @param min Minimim value
	 * @param max Maximim value.  Must be greater than min.
	 * @return Integer between min and max, inclusive.
	 * @see java.util.Random#nextInt(int)
	 */
	public static int randInt(int min, int max) {

	    // Usually this can be a field rather than a method variable
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	
	
	public static String RandomNameGenerator() {
		 String[] names = new String[54];

		 names[0] = "Snipar";
		 names[1] = "MLG";
		 names[2] = "BL4zE";
		 names[3] = "Ayy";
		 names[4] = "LMA0";
		 names[5] = "#R3kt";
		 names[6] = "Ku$H";
		 names[7] = "L0RD";
		 names[8] = "G4BeN";
		 names[9] = "Gabe";
		 names[10] = "Kawaii";
		 names[11] = "Killa";
		 names[12] = "Noob";
		 names[13] = "Pro";
		 names[14] = "9/11";
		 names[15] = "Weed";
		 names[16] = "Doge";
		 names[17] = "KaWaii";
		 names[18] = "kek";
		 names[19] = "TOP";
		 names[20] = "Loli";
		 names[21] = "C0D";
		 names[22] = "Global";
		 names[23] = "Magg0t";
		 names[24] = "Goyim";
		 names[25] = "0bama";
		 names[26] = "#Shrekt";
		 names[27] = "Sug0i";
		 names[28] = "d0g3";
		 names[29] = "Solid";
		 names[30] = "NavySeal";
		 names[31] = "Elite";
		 names[32] = "BlackOps";
		 names[33] = "#Y0L0";
		 names[34] = "Tr0LL";
		 names[35] = "Retard";
		 names[36] = "Hurr";
		 names[37] = "Woof";
		 names[38] = "Senpai";
		 names[39] = "Baka";
		 names[40] = "Desu";
		 names[41] = "Swag";
		 names[42] = "#Swag";
		 names[43] = "Weeabo";
		 names[44] = "Weeb";
		 names[45] = "CS:GO";
		 names[46] = "Gangsta";
		 names[47] = "Level99";
		 names[48] = "Moe";
		 names[49] = "IsKill";
		 names[50] = "Hard";
		 names[51] = "Crusher";
		 names[52] = "420";
		 names[53] = "1337";
		 
		 
		 return new String("xXx_" + names[randInt(0,53)] + names[randInt(0,53)] +"_xXx");
		
	}

}