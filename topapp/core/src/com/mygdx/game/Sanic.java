package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

public class Sanic {
	
	private Texture sanicTexture;
    private Sprite sanicSprite;
    
    private BodyDef bodyDef = new BodyDef();
    private Body body;
    
    public Sanic(World world, float x, float y){
    	sanicTexture = new Texture("assets/sanic.png");
    	sanicSprite = new Sprite(sanicTexture);
    	
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(x / TopApp.PIXELS_TO_METERS, y / TopApp.PIXELS_TO_METERS);
        body = world.createBody(bodyDef);
        
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sanicSprite.getWidth()/2 / TopApp.PIXELS_TO_METERS, sanicSprite.getHeight()/2 / TopApp.PIXELS_TO_METERS);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f; 
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.2f; // Make it bounce a little bit


        // Create our fixture and attach it to the body
        body.createFixture(fixtureDef);
        

    }
    
    public void render(Batch batch) {
    	
    	sanicSprite.setPosition((body.getPosition().x * TopApp.PIXELS_TO_METERS) - sanicSprite.getWidth()/2 ,
    	        (body.getPosition().y * TopApp.PIXELS_TO_METERS) -sanicSprite.getHeight()/2 );
    	sanicSprite.setRotation((float)Math.toDegrees(body.getAngle()));
    	
    	sanicSprite.draw(batch);
    }

}
