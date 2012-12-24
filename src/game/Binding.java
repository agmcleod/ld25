package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class Binding {
	private Rectangle bounds;
	// store the action key, as well as the keycode
	String key;
	int keyCode;
	
	public Binding(String key, int keyCode) {
		this.key = key;
		this.keyCode = keyCode;
	}
	
	public void drawBounds(ShapeRenderer sr) {
		sr.rect(bounds.x, bounds.y, bounds.width, bounds.height);
	}
	
	public String textCharacter() {
		return "";
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
}
