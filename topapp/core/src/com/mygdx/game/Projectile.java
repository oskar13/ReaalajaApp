package com.mygdx.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Projectile {
	private Texture projectileTexture;
    private Sprite projectileSprite;
    private BodyDef bodyDef = new BodyDef();
    Body body;
    private Fixture fixture;
    
    final float PIXELS_TO_METERS = 100f;
	
	// constructor
	
	public Projectile(World world, float originX, float originY) {
		
		Random rand = new Random();
		
		int  n = 1 + rand.nextInt((3 - 1) + 1);
		
		projectileTexture = new Texture(Gdx.files.internal("assets/projectiles/" + n + ".png"));
        projectileSprite = new Sprite(projectileTexture);
        
        
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyType.DynamicBody;
        // Set our body's starting position in the world
        //bodyDef.position.set(originX, originY);
        
        bodyDef.position.set((originX + projectileSprite.getWidth()/2) / PIXELS_TO_METERS,
        (originY + projectileSprite.getHeight()/2) / PIXELS_TO_METERS);
        
        body = world.createBody(bodyDef);
        body.setUserData(7);
        
        
        body.setTransform(body.getPosition().x, body.getPosition().y, getAngleRadians(TopApp.getWorldMouse().x/100f, originX/100f, TopApp.getWorldMouse().y/100f, originY/100f));

        
        
        // Create a circle shape and set its radius to 6
        //CircleShape circle = new CircleShape();
        //circle.setRadius(0.5f);
        
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(projectileSprite.getWidth()/2 / PIXELS_TO_METERS, projectileSprite.getHeight()/2 / PIXELS_TO_METERS);
        
        
        

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 10f; 
        fixtureDef.friction = 0.7f;
        fixtureDef.restitution = 0.2f; // Make it bounce a little bit


        // Create our fixture and attach it to the body
        fixture = body.createFixture(fixtureDef);

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        //circle.dispose();
        
        
        

        System.out.println("Mouse pos game X" +  TopApp.getWorldMouse().x + "   Y " +  TopApp.getWorldMouse().y );
        
        
        

        

        
        //body.applyLinearImpulse(TopApp.getWorldMouse().x, TopApp.getWorldMouse().y,originX, originY,  true);
        body.setLinearVelocity((TopApp.getWorldMouse().x - originX)/100f, (TopApp.getWorldMouse().y - originY)/100f);

        
        


	}
	
	public void drawProjectile(SpriteBatch batch) {

				
		projectileSprite.setPosition((body.getPosition().x * PIXELS_TO_METERS) - projectileSprite.getWidth()/2 ,
        (body.getPosition().y * PIXELS_TO_METERS) -projectileSprite.getHeight()/2 );
		projectileSprite.setRotation((float)Math.toDegrees(body.getAngle()));
		
		projectileSprite.draw(batch);
	}
	
	
	public float getPosition() {
		//System.out.println(body.getAngle());
		return body.getPosition().y;
		
		
	}
	
	
    public float getAngleRadians(float x1, float y1, float x2, float y2) {
        float angle = (float) Math.atan2(y2 - y1, x2 - x1);

        if(angle < 0){
            angle += 360;
        }

        return angle;
    }
	
}
