package game;
import com.badlogic.gdx.math.Rectangle;

public class Tile extends Rectangle {
	private int texture;
	
	public Tile() {
		
	}

	public int getTexture() {
		return texture;
	}

	public void setTexture(int texture) {
		this.texture = texture;
	}
}
