package com.mygdx.game;

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
	private int mass;
	private float posX;
	private float posY;
	private int speedX;
	private int speedY;
	private Texture projectileTexture;
    private Sprite projectileSprite;
    private BodyDef bodyDef = new BodyDef();
    private Body body;
    
    final float PIXELS_TO_METERS = 100f;
	
	// constructor
	
	public Projectile(World world, float originX, float originY) {
		projectileTexture = new Texture(Gdx.files.internal("assets/tankWheel.png"));
        projectileSprite = new Sprite(projectileTexture);
        
        //projectileSprite.setSize(projectileSprite.getWidth() * 0.5f,	projectileSprite.getHeight() * 0.5f);
        
        


        
        
        // We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyType.DynamicBody;
        // Set our body's starting position in the world
        //bodyDef.position.set(originX, originY);
        
        bodyDef.position.set((originX + projectileSprite.getWidth()/2) / PIXELS_TO_METERS,
        (originY + projectileSprite.getHeight()/2) / PIXELS_TO_METERS);
        
        
        body = world.createBody(bodyDef);

        
        
        // Create a circle shape and set its radius to 6
        //CircleShape circle = new CircleShape();
        //circle.setRadius(0.5f);
        
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(projectileSprite.getWidth()/2 / PIXELS_TO_METERS, projectileSprite.getHeight()/2 / PIXELS_TO_METERS);
        
        
        

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 10f; 
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.2f; // Make it bounce a little bit


        // Create our fixture and attach it to the body
        Fixture fixture = body.createFixture(fixtureDef);

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        //circle.dispose();
        
        
        

        System.out.println("Mouse pos game X" +  TopApp.getWorldMouse().x + "   Y " +  TopApp.getWorldMouse().y );
        
        
        body.applyLinearImpulse(originX, originY, TopApp.getWorldMouse().x , TopApp.getWorldMouse().y , true);

        

	}
	
	public void drawProjectile(SpriteBatch batch) {
		posX += speedX;
		posY += speedY;
				
		
		//projectileSprite.setX(posX);
		//projectileSprite.setY(posY);
		
		//projectileSprite.setPosition(body.getPosition().x, body.getPosition().y);
		//projectileSprite.setRotation(body.getAngle() * MathUtils.radDeg);
		
		projectileSprite.setPosition((body.getPosition().x * PIXELS_TO_METERS) - projectileSprite.getWidth()/2 ,
        (body.getPosition().y * PIXELS_TO_METERS) -projectileSprite.getHeight()/2 );
		projectileSprite.setRotation((float)Math.toDegrees(body.getAngle()));
		
		projectileSprite.draw(batch);
	}
	
	
	public float getPosition() {
		//System.out.println(body.getAngle());
		return body.getPosition().y;
		
		
	}
	
}
