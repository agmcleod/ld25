package game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Array;

public class Sprite {
	private int x;
	private int y;
	private int width;
	private int height;
	private Texture textureImage;
	private Array<Animation> animations;
	private Array<TextureRegion> frames;
	private int focusedAnimation;
	private boolean animated;
	
	public Sprite() {
		animations = new Array<Animation>();
		this.setAnimated(true);
		frames = new Array<TextureRegion>();
	}
	
	public Sprite(int x, int y, int width, int height, Texture textureImage, boolean animated) {
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
		this.setTextureImage(textureImage);
		focusedAnimation = 0;
		this.animated = animated;
		if(this.animated) {
			animations = new Array<Animation>();
		}
		else {
			frames = new Array<TextureRegion>();
		}
	}
	
	public void addAnimation(int[][] coords) {
		TextureRegion[] frames = new TextureRegion[coords.length];
		for(int i = 0; i < coords.length; i++) {
			frames[i] = new TextureRegion(textureImage, coords[i][0] * 32, coords[i][1] * 32, 32, 32);
		}
		this.animations.add(new Animation(0.025f, frames));
	}
	
	public void addFrame(int x, int y, int width, int height) {
		TextureRegion region = new TextureRegion(this.textureImage, x, y, width, height);
		this.frames.add(region);
	}
	
	public Rectangle getCollisionRectangle() {
		return new Rectangle(this.x, this.y, this.width, this.height);
	}

	public int getHeight() {
		return height;
	}

	public Texture getTextureImage() {
		return textureImage;
	}

	public int getWidth() {
		return width;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	// call between a sprite batch begin & end
	public void render(float stateTime, SpriteBatch batch) {
		TextureRegion currentFrame;
		if(this.animated) {
			Animation currentAnimation = this.animations.get(focusedAnimation);
			currentFrame = currentAnimation.getKeyFrame(stateTime, true); 
		}
		else {
			currentFrame = frames.get(focusedAnimation);
		}
		batch.draw(currentFrame, x, y);
	}
	
	public void setFocusedAnimation(int frame) {
		this.focusedAnimation = frame;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setTextureImage(Texture textureImage) {
		this.textureImage = textureImage;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isAnimated() {
		return animated;
	}

	public void setAnimated(boolean animated) {
		this.animated = animated;
	}
}
