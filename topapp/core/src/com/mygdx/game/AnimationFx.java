package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationFx {
	private int        FRAME_COLS;         // #1
    private int        FRAME_ROWS;         // #2
    
    float x;
    float y;

    Animation                       spriteAnimation;          // #3
    Texture                         spriteSheet;              // #4
    TextureRegion[]                 animationFrames;             // #5
    TextureRegion                   currentFrame;           // #7
    
    Boolean kill = false;

    float stateTime;                                        // #8

    public AnimationFx(String path,int cols,int rows, float worldX, float worldY) {

    	this.FRAME_COLS = cols;
    	this.FRAME_ROWS = rows;
        spriteSheet = new Texture(Gdx.files.internal(path)); // #9
        TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth()/FRAME_COLS, spriteSheet.getHeight()/FRAME_ROWS);              // #10
        animationFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                animationFrames[index++] = tmp[i][j];
            }
        }
        spriteAnimation = new Animation(0.025f, animationFrames);      // #11
		//spriteAnimation.setPlayMode( PlayMode.LOOP_RANDOM);
        stateTime = 0f;                         // #13
        
        
        
    	this.x = worldX*TopApp.PIXELS_TO_METERS-(spriteSheet.getWidth()/FRAME_COLS/2);
    	this.y = worldY*TopApp.PIXELS_TO_METERS-(spriteSheet.getHeight()/FRAME_ROWS/2);
    }
    
    public AnimationFx(String path,int cols,int rows, float worldX, float worldY, float speed) {

    	this.FRAME_COLS = cols;
    	this.FRAME_ROWS = rows;
        spriteSheet = new Texture(Gdx.files.internal(path)); // #9
        TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth()/FRAME_COLS, spriteSheet.getHeight()/FRAME_ROWS);              // #10
        animationFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                animationFrames[index++] = tmp[i][j];
            }
        }
        spriteAnimation = new Animation(speed, animationFrames);      // #11
		//spriteAnimation.setPlayMode( PlayMode.LOOP_RANDOM);
        stateTime = 0f;                         // #13
        
        
        
    	this.x = worldX*TopApp.PIXELS_TO_METERS-(spriteSheet.getWidth()/FRAME_COLS/2);
    	this.y = worldY*TopApp.PIXELS_TO_METERS-(spriteSheet.getHeight()/FRAME_ROWS/2);
    }


    public void draw(Batch batch) {
        stateTime += Gdx.graphics.getDeltaTime();           // #15
        currentFrame = spriteAnimation.getKeyFrame(stateTime, true);  // #16
        
        

        batch.draw(currentFrame, x, y);             // #17
        

        
        if (spriteAnimation.getKeyFrameIndex(stateTime) == (FRAME_COLS*FRAME_ROWS-1)) {
			kill= true;
		}


    }
    
    
    public Boolean isKill() {
    	return kill;
    }
}
