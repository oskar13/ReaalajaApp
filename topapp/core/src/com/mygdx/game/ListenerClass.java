package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class ListenerClass implements ContactListener {

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
		
		
		
	    Body a=contact.getFixtureA().getBody();
	    Body b=contact.getFixtureB().getBody();
	    a.getUserData();
	    if(a.getUserData() instanceof Projectile&&b.getUserData() instanceof Target)
	    {
	    	System.out.println("ayy lmao");
	    }
	    
	    MyUserTags myObject = (MyUserTags) a.getUserData();
	    
	    //System.out.println(((Projectile) a.getUserData()).getPosition());
	    //myObject.bodyID = value;
	    
	    //System.out.println("ayy" + myObject);
	    
	    System.out.println(a.getUserData());
	    
	    if (a.getUserData() != null) {
	    	System.out.println("Skoor!!!!!");
	    	TopApp.Skoor +=1;

	    	
	    }
		
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}
}