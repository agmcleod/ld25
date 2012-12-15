import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


public class Main {
	public static void main(String args[]) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.width = 800;
		cfg.height = 600;
		cfg.title = "Ludum Dare 25 - You are the villain - by agmcleod";
		cfg.useGL20 = true;
		new LwjglApplication(new Game(), cfg);
	}
}
