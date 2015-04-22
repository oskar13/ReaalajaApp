package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Tank {
	
    private Texture hullTexture;
    private Sprite hullSprite;
    
    private Texture barrelTexture;
    private Sprite barrelSprite;
   
    
    private int fireRate = 10;
    private int fireRateCooldown = 0;
    
    private BodyDef bodyDef = new BodyDef();
    private Body body;
    
    final float PIXELS_TO_METERS = 100f;
    
    public Tank(World world, int x, int y) {

   	
    	hullTexture = new Texture(Gdx.files.internal("assets/tankHull.png"));
        hullSprite = new Sprite(hullTexture);
        
        barrelTexture = new Texture(Gdx.files.internal("assets/tankBarrel.png"));
        barrelSprite = new Sprite(barrelTexture);
        
        
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyType.DynamicBody;
        // Set our body's starting position in the world
        //bodyDef.position.set(originX, originY);
        
        bodyDef.position.set(x / PIXELS_TO_METERS,
        y / PIXELS_TO_METERS);
        
        
        body = world.createBody(bodyDef);

        
        
        // Create a circle shape and set its radius to 6
        //CircleShape circle = new CircleShape();
        //circle.setRadius(0.5f);
        
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(hullSprite.getWidth()/2 / PIXELS_TO_METERS, hullSprite.getHeight()/2 / PIXELS_TO_METERS);
        
        
        

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 10f; 
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.2f; // Make it bounce a little bit


        // Create our fixture and attach it to the body
        Fixture fixture = body.createFixture(fixtureDef);
        
        barrelSprite.setOrigin(0f, barrelSprite.getHeight()/2);
    }
    
    public void drawTank(SpriteBatch batch) {
    	
    	
    	
    	barrelSprite.setY(body.getPosition().y+hullSprite.getHeight()/2);
    	barrelSprite.setX(body.getPosition().x+hullSprite.getWidth()/2);
    	barrelSprite.setRotation(getAngle(body.getPosition().x+hullSprite.getWidth()/2, body.getPosition().y+hullSprite.getHeight()/2, Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY()));
    	
    	

    	
    	
    	
    	
    	hullSprite.setPosition((body.getPosition().x * PIXELS_TO_METERS) - hullSprite.getWidth()/2 ,
    	        (body.getPosition().y * PIXELS_TO_METERS) -hullSprite.getHeight()/2 );
    	hullSprite.setRotation((float)Math.toDegrees(body.getAngle()));
    	
    	
    	

    	
    	barrelSprite.setPosition((body.getPosition().x * PIXELS_TO_METERS) ,
    	        (body.getPosition().y * PIXELS_TO_METERS) );
    	
    	
    	Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        //TopApp.camera.unproject(mousePos).x
    	
    	barrelSprite.setRotation(getAngle(body.getPosition().x * PIXELS_TO_METERS, body.getPosition().y * PIXELS_TO_METERS, TopApp.camera.unproject(mousePos).x, Gdx.graphics.getHeight()-TopApp.camera.unproject(mousePos).y));
    
    	
    	barrelSprite.draw(batch);
    	hullSprite.draw(batch);
    	
    	
    	
    	if (fireRateCooldown > 0 ) {
    		fireRateCooldown -= 1;
    	} 
    	

    }
    
    
    public void driveForward() {
    	//posX += 1;
    }
    
    public void driveBack() {
    	//posX -= 1;
    }
    
    public float getX() {
    	return hullSprite.getX();
    }
    
    public void shoot(World world, ArrayList list) {
    	
    	if (fireRateCooldown == 0) {
    			list.add(new Projectile(world, (body.getPosition().x * PIXELS_TO_METERS),(body.getPosition().y * PIXELS_TO_METERS)+100));
    			System.out.println(" tank X " + body.getPosition().x * PIXELS_TO_METERS + " tank Y " + body.getPosition().y * PIXELS_TO_METERS);
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
