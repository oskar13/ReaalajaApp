package com.mygdx.game;

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
	SpriteBatch batch;
	Sprite sprite;
	Texture img, imgback;
	World world;
	Body body;
	
	@Override
	public void create () {
		

		
		
		batch = new SpriteBatch();
		img = new Texture("assets/doge.png");
		imgback = new Texture("assets/background.jpg");
		world = new World(new Vector2(0, -98f), true);
		
		
		sprite = new Sprite(img);
		
		sprite.setPosition(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2,  Gdx.graphics.getHeight() / 2);
		sprite.setScale(0.3f);
		
        // Now create a BodyDefinition.  This defines the physics objects type         and position in the simulation
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // We are going to use 1 to 1 dimensions.  Meaning 1 in physics engine         is 1 pixel
        // Set our body to the same position as our sprite
        bodyDef.position.set(sprite.getX(), sprite.getY());

        // Create a body in the world using our definition
        body = world.createBody(bodyDef);

        // Now define the dimensions of the physics shape
        PolygonShape shape = new PolygonShape();
        // We are a box, so this makes sense, no?
        // Basically set the physics polygon to a box with the same dimensions     as our sprite
        shape.setAsBox(sprite.getWidth()/2, sprite.getHeight()/2);

        // FixtureDef is a confusing expression for physical properties
        // Basically this is where you, in addition to defining the shape of the         body
        // you also define it's properties like density, restitution and others         we will see shortly
        // If you are wondering, density and area are used to calculate over all         mass
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        Fixture fixture = body.createFixture(fixtureDef);

        // Shape is the only disposable of the lot, so get rid of it
        shape.dispose();
		
		
		
		
	}
	int i =1;
	int k = 1;
	int dogex=1;
	int dogey=1;
	Boolean pomf= false;

	 
	
	

	

	@Override
	public void render () {
		
        // update rate to the frame rate, and vice versa
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);

        // Now update the spritee position accordingly to it's now updated        Physics body
        sprite.setPosition(body.getPosition().x, body.getPosition().y);
		
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.enableBlending();
		batch.begin();
		
		batch.draw(imgback,k						,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(imgback,Gdx.graphics.getWidth()+k,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		k-=1;
		if (k<=-Gdx.graphics.getWidth()) {
			k=0;
		}
		
		batch.draw(img, Gdx.input.getX() , -Gdx.input.getY());
		batch.draw(img, 0, i);
		batch.draw(img, 0, -i);
		batch.draw(img, dogex, -dogey);
		batch.setColor( new Color(0.4f, 0.4f, 0.4f, 0.4f));
		
		   System.out.println("Normal getX(): " + Gdx.input.getX()); 
		   System.out.println("Normal getY(): " + Gdx.input.getY()); 
		   
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			System.out.println("CLICK!!!!");

			
			body.setLinearVelocity(0.f, 200.f);
			
			
		}

		
		//batch.draw(img, x, y, width, height, srcX, srcY, srcWidth, srcHeight, flipX, flipY);

		
		if (dogex < Gdx.input.getX()) {
			dogex+=1;
		} else if (dogex > Gdx.input.getX()) {
			dogex-=1;
		}
		
		if (dogey > Gdx.input.getY()) {
			dogey-=1;
		} else if (dogey < Gdx.input.getY()) {
			dogey+=1;
		}
		
		
		if (i>3 || pomf) {
			pomf = true;
			i-=1;
			if (i<0) {
				pomf = false;
			}
		} else {
			i+=1;
		}
		
		
		
		batch.draw(sprite, sprite.getX(), sprite.getY());
		
		
		batch.end();
	}
}
