package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;



public class Achievement {
	
	private String path;
	private Texture achievementTexture;
	private Sprite achievementSprite;
	private int lifeTime = 600;
	private float yOffset;
	public Boolean isKill = false;
	
	public Achievement(AchievementType type) {
		
	    switch (type)
	    {
	      case BLAZEIT:		path="assets/achievements/blazeit.png";
	      break;
	      case HEADPHONES:	path="assets/achievements/headphones.png";
	      break;
	      case MOM:		path="assets/achievements/mom.png";
	      break;
	      case QUICKTIME: path="assets/achievements/quicktime.png";
	      break;
	      case SENPAI:  	path="assets/achievements/senpai.png";
	      break;
	      case TRIPLE:  	path="assets/achievements/triple.png";
	      break;
	      default:        path="assets/achievements/error.png";;
	    }
	    achievementTexture  = new Texture(path);
	    
		achievementSprite = new Sprite(achievementTexture);
	}
	
	public void render(Batch batch){
		
		achievementSprite.setPosition(TopApp.cam.position.x+(Gdx.graphics.getWidth()/2)-achievementSprite.getWidth(), 
						TopApp.cam.position.y+(Gdx.graphics.getHeight()/2)-yOffset);
		
		//achievementSprite.getHeight()
		if (lifeTime > 0) {
			if (yOffset < achievementSprite.getHeight()+20) {
				yOffset += 1.5f;
				float trans = yOffset/(achievementSprite.getHeight()+20);
				if (trans >1f) {
					trans = 1f;
				}
				achievementSprite.setColor(1, 1, 1, trans);
			}
			



			lifeTime -=1;
		} else {

			if (yOffset > 0) {
				yOffset -= 1.5f;
				
				achievementSprite.setColor(1, 1, 1, yOffset/(achievementSprite.getHeight()+20));
			} else {
				isKill = true;
			}
		}

		

		
		achievementSprite.draw(batch);
		
	};
	
	public static enum AchievementType { BLAZEIT, HEADPHONES, MOM, QUICKTIME, SENPAI, TRIPLE };

}
