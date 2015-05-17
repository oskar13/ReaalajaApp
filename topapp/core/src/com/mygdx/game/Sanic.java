package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
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
    Body body;
    
    int ttlDead = 180;
    Boolean isKill = false;
    String name = "Sample Text";
    
    public Sanic(World world, float worldX, float worldY){
    	sanicTexture = new Texture("assets/sanic.png");
    	sanicSprite = new Sprite(sanicTexture);
    	
    	name = TopApp.RandomNameGenerator();
    	
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(worldX, worldY);
        body = world.createBody(bodyDef);
        body.setUserData(9);
        
        body.setUserData(666);
        
        
        Vector2[] vertices = new Vector2[6];

        vertices[0] = new Vector2(-0.9f, -0.9f);
        vertices[1] = new Vector2(-1f, 0f);
        vertices[2] = new Vector2(-0.4f, 1f);
        vertices[3] = new Vector2(0.4f, 0.8f);
        vertices[4] = new Vector2(1f, 0f);
        vertices[5] = new Vector2(0.8f, -0.9f);
		PolygonShape shape = new PolygonShape();
		shape.set(vertices);
        

        //shape.setAsBox(sanicSprite.getWidth()/2 / TopApp.PIXELS_TO_METERS, sanicSprite.getHeight()/2 / TopApp.PIXELS_TO_METERS);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.8f; 
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.2f; // Make it bounce a little bit


        // Create our fixture and attach it to the body
        body.createFixture(fixtureDef);
        

    }
    
    public void render(Batch batch) {
    	
    	//body.setTransform(TopApp.posX-body.getPosition().x,0 /*body.getPosition().y+TopApp.posY*/, 0);
    	
    	
        float velocity = 1;

        Vector2 sp = new Vector2(body.getPosition());
        Vector2 jp = new Vector2(TopApp.posX,TopApp.posY);

        Vector2 delta = jp.sub(sp).nor(); // This is not a unit vector pointing in the direction from ap to mp

        // Take alien current position and add the delta times velocity times delta-time
        Vector2 newPos = new Vector2(body.getPosition());
        //newPos.add(delta);
        newPos.add(delta.scl(velocity * Gdx.graphics.getDeltaTime()));
        
        body.setTransform(newPos, body.getAngle());
    	
    	sanicSprite.setPosition((body.getPosition().x * TopApp.PIXELS_TO_METERS) - sanicSprite.getWidth()/2 ,
    	        (body.getPosition().y * TopApp.PIXELS_TO_METERS) -sanicSprite.getHeight()/2 );
    	sanicSprite.setRotation((float)Math.toDegrees(body.getAngle()));
        	
    	if (body.getUserData().equals(999)) {
    		sanicSprite.setColor(1f,0.2f,0.2f,0.4f);	
    		ttlDead -= 1;
		}
    	
    	if (ttlDead == 0 ) {
    		
    		isKill = true;
    	}
    	
    	sanicSprite.draw(batch);
    }
    
    public void renderName(Batch batch, BitmapFont font) {
    	
  	
    	font.setColor(new Color().YELLOW);
        font.draw(batch, name, (body.getPosition().x * TopApp.PIXELS_TO_METERS) - sanicSprite.getWidth()/2,
    	        (body.getPosition().y * TopApp.PIXELS_TO_METERS) +sanicSprite.getHeight()/1.7f);

    }
    
    public float getY() {
    	return body.getPosition().y;
    }

}
