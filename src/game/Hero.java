package game;

import com.badlogic.gdx.graphics.Texture;

public class Hero extends Sprite {
	private float facingDirection;
	
	public Hero(int x, int y, int width, int height, Texture textureImage, boolean animated) {
		super(x, y, width, height, textureImage, animated);
	}

	public float getFacingDirection() {
		return facingDirection;
	}

	public void setFacingDirection(float facingDirection) {
		this.facingDirection = facingDirection;
	}
}
