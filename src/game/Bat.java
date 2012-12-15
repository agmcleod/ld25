package game;

import com.badlogic.gdx.graphics.Texture;

public class Bat extends Sprite {
	public Bat(int x, int y, Texture texture) {
		super(x, y, 32, 32, texture, true);
		int[][] coords = new int[11][2];
		this.health = 2;
		this.maxHealth = 2;
		coords[0][0] = 2;
		coords[0][1] = 0;
		coords[1][0] = 3;
		coords[1][1] = 0;
		coords[2][0] = 4;
		coords[2][1] = 0;
		coords[3][0] = 5;
		coords[3][1] = 0;
		coords[4][0] = 6;
		coords[4][1] = 0;
		coords[5][0] = 7;
		coords[5][1] = 0;
		coords[6][0] = 6;
		coords[6][1] = 0;
		coords[7][0] = 5;
		coords[7][1] = 0;
		coords[8][0] = 4;
		coords[8][1] = 0;
		coords[9][0] = 3;
		coords[9][1] = 0;
		coords[10][0] = 2;
		coords[10][1] = 0;
		this.animationSpeed = 0.05f;
		this.movementSpeed = 300;
		
		addAnimation(coords);
	}
}
