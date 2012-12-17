package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
	protected float animationSpeed = 0.025f;
	protected int movementSpeed;
	protected int health = 3;
	protected int maxHealth;
	public boolean isColliding;
	
	public Sprite() {
		animations = new Array<Animation>();
		this.setAnimated(true);
		frames = new Array<TextureRegion>();
		this.maxHealth = this.health;
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
		this.maxHealth = this.health;
	}
	
	public void addAnimation(int[][] coords) {
		TextureRegion[] frames = new TextureRegion[coords.length];
		for(int i = 0; i < coords.length; i++) {
			frames[i] = new TextureRegion(textureImage, coords[i][0] * 32, coords[i][1] * 32, 32, 32);
		}
		this.animations.add(new Animation(this.animationSpeed, frames));
	}
	
	public void addFrame(int x, int y, int width, int height) {
		TextureRegion region = new TextureRegion(this.textureImage, x, y, width, height);
		this.frames.add(region);
	}
	
	public String collisionRectangleString() {
		Rectangle r = getCollisionRectangle();
		return " x : " + r.x + " y: " + r.y + " width: " + r.width + " height: " + r.height; 
	}
	
	public void drawHealthBar(ShapeRenderer renderer) {
		renderer.begin(ShapeRenderer.ShapeType.FilledRectangle);
		renderer.setColor(Color.BLACK);
		renderer.filledRect(this.x, this.y - 13, this.width, 8);
		renderer.end();
		renderer.begin(ShapeRenderer.ShapeType.FilledRectangle);
		renderer.setColor(Color.GREEN);
		renderer.filledRect(this.x+1, this.y - 12, (int) ((this.width * ((float) this.health / (float) this.maxHealth)) - 2), 6);
		renderer.end();
	}
	
	public Rectangle getCollisionRectangle() {
		return new Rectangle(this.x, this.y, this.width, this.height);
	}
	
	public int getHealth() {
		return health;
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
	
	public boolean isOn(int x, int y) {
		return (this.x == x && this.y == y);
	}
	
	public boolean moveTo(int x, int y) {
		if(!isOn((int) x, (int) y)) {
			float angle = MathUtils.atan2(y - this.y, x - this.x);
			angle = angle * (180/MathUtils.PI);
			if(angle < 0)
			{
			    angle = 360 - (-angle);
			}
			
			int velocityX = (int) (MathUtils.cos(angle * MathUtils.PI / 180) * this.movementSpeed * Gdx.graphics.getDeltaTime());
			int velocityY = (int) (MathUtils.sin(angle * MathUtils.PI / 180) * this.movementSpeed * Gdx.graphics.getDeltaTime());
			this.x += velocityX;
			this.y += velocityY;
			// check if sprite went past
			
			// if the previous x coordinate is less than the current one (meaning sprite is moving right)
			// and the x passed the target, set it to the target
			if(this.x - velocityX < this.x && this.x > x) {
				this.x = x;
			}
			// otherwise if the previous coordinate is greater than, meaning it's moving left
			// and the x passed the target, set it to the target
			else if (this.x - velocityX > this.x && this.x < x) {
				this.x = x;
			}
			
			// if the previous y coordinate is less than the current one (sprite is moving up)
			// and it passed the target, set it to the target
			if(this.y - velocityY < this.y && this.y > y) {
				this.y = y;
			}
			// otherwise if the previous y coordinate is greater than the current one (sprite moving down)
			// and it passed the target, set it
			if(this.y - velocityY > this.y && this.y < y) {
				this.y = y;
			}
			return false;
		}
		else {
			return true;
		}
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
