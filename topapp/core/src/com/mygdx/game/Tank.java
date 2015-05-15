package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

public class Tank {
	
    private Texture hullTexture;
    private Sprite hullSprite;
    
    private Texture barrelTexture;
    private Sprite barrelSprite;
    
    
    private Texture wheel1Texture;
    private Sprite wheel1Sprite;
    private Sprite wheel2Sprite;
   
    
    private int fireRate = 1;
    private int fireRateCooldown = 0;
    
    private BodyDef bodyDef = new BodyDef();
    private Body body;
    
    
    private Body wheel1, wheel2;
    private Joint motor1;
    
    
    final float PIXELS_TO_METERS = 100f;
    
    public Tank(World world, int x, int y) {

   	
    	hullTexture = new Texture(Gdx.files.internal("assets/tankHull.png"));
        hullSprite = new Sprite(hullTexture);
        
        barrelTexture = new Texture(Gdx.files.internal("assets/tankBarrel.png"));
        barrelSprite = new Sprite(barrelTexture);
        
        wheel1Texture = new Texture(Gdx.files.internal("assets/tankWheel.png"));
        wheel1Sprite = new Sprite(wheel1Texture);
        wheel2Sprite = new Sprite(wheel1Texture);
        
        
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyType.DynamicBody;
        // Set our body's starting position in the world
        //bodyDef.position.set(originX, originY);
        
        bodyDef.position.set(x / PIXELS_TO_METERS,
        y / PIXELS_TO_METERS);
        
        
        body = world.createBody(bodyDef);
        
        bodyDef.position.set(x / PIXELS_TO_METERS,  y / PIXELS_TO_METERS);
        wheel1 = world.createBody(bodyDef);
        wheel2 = world.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(wheel1Sprite.getWidth()/2/ PIXELS_TO_METERS);
        
        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDefWheel = new FixtureDef();
        fixtureDefWheel.shape = circle;
        fixtureDefWheel.density = 10f; 
        fixtureDefWheel.friction = 0.9f;
        fixtureDefWheel.restitution = 0.2f; // Make it bounce a little bit
        
     // Create our fixture and attach it to the body
        wheel1.createFixture(fixtureDefWheel);
        wheel2.createFixture(fixtureDefWheel);
        
        circle.dispose();
        
        
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(hullSprite.getWidth()/2 / PIXELS_TO_METERS, hullSprite.getHeight()/2 / PIXELS_TO_METERS);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 10f; 
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.2f; // Make it bounce a little bit


        // Create our fixture and attach it to the body
        body.createFixture(fixtureDef);
        
        barrelSprite.setOrigin(0f, barrelSprite.getHeight()/2);
        
        
        
        
        

        RevoluteJointDef rjd=new RevoluteJointDef();
        rjd.bodyA=body;
        rjd.bodyB=wheel1;
        rjd.localAnchorA.set(hullSprite.getWidth()/3 / PIXELS_TO_METERS,-hullSprite.getHeight()/2 / PIXELS_TO_METERS);
        rjd.localAnchorB.set(0,0);
        rjd.collideConnected=false;
        world.createJoint(rjd);
        
        rjd.bodyA=body;
        rjd.bodyB=wheel2;
        rjd.localAnchorA.set(-hullSprite.getWidth()/3 / PIXELS_TO_METERS,-hullSprite.getHeight()/2 / PIXELS_TO_METERS);
        rjd.localAnchorB.set(0,0);
        rjd.collideConnected=false;
        world.createJoint(rjd);



        
    }
    
    public void drawTank(SpriteBatch batch) {
    	
    	
    	
    	barrelSprite.setY(body.getPosition().y+hullSprite.getHeight()/2);
    	barrelSprite.setX(body.getPosition().x+hullSprite.getWidth()/2);
    	barrelSprite.setRotation(getAngle((body.getPosition().x * PIXELS_TO_METERS)+hullSprite.getWidth()/2, (body.getPosition().y * PIXELS_TO_METERS)+hullSprite.getHeight()/2, TopApp.getWorldMouse().x, TopApp.getWorldMouse().y));
    	
    	
//System.out.println(getAngle((body.getPosition().x * PIXELS_TO_METERS)+hullSprite.getWidth()/2, (body.getPosition().y * PIXELS_TO_METERS)+hullSprite.getHeight()/2, TopApp.getWorldMouse().x, TopApp.getWorldMouse().y));
    	
    	
    	
    	
    	hullSprite.setPosition((body.getPosition().x * PIXELS_TO_METERS) - hullSprite.getWidth()/2 ,
    	        (body.getPosition().y * PIXELS_TO_METERS) -hullSprite.getHeight()/2 );
    	hullSprite.setRotation((float)Math.toDegrees(body.getAngle()));
    	
    	
    	
    	wheel1Sprite.setPosition((wheel1.getPosition().x * PIXELS_TO_METERS) - wheel1Sprite.getWidth()/2 ,
    	        (wheel1.getPosition().y * PIXELS_TO_METERS) -wheel1Sprite.getHeight()/2 );
    	wheel1Sprite.setRotation((float)Math.toDegrees(wheel1.getAngle()));
    	
    	wheel2Sprite.setPosition((wheel2.getPosition().x * PIXELS_TO_METERS) - wheel2Sprite.getWidth()/2 ,
    	        (wheel2.getPosition().y * PIXELS_TO_METERS) -wheel2Sprite.getHeight()/2 );
    	wheel2Sprite.setRotation((float)Math.toDegrees(wheel2.getAngle()));
    	
    	
    	

    	
    	barrelSprite.setPosition((body.getPosition().x * PIXELS_TO_METERS) ,
    	        (body.getPosition().y * PIXELS_TO_METERS) );
    	
    	

    	
    	barrelSprite.setRotation(getAngle(body.getPosition().x * PIXELS_TO_METERS, body.getPosition().y * PIXELS_TO_METERS, TopApp.getWorldMouse().x, TopApp.getWorldMouse().y));

    	
    	
    	

    		//wheel1.applyTorque(0.01f, true);


    	
    	
    	
    	barrelSprite.draw(batch);
    	hullSprite.draw(batch);
    	
    	wheel1Sprite.draw(batch);
    	wheel2Sprite.draw(batch);
    	
    	
    	
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
