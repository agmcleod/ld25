package game;
import com.badlogic.gdx.Game;


public class MyGame extends Game {
	
	GameScreen gameScreen;
	MainMenuScreen mainMenuScreen;
	boolean loadedGameScreen = false;

	@Override
	public void create() {
		mainMenuScreen = new MainMenuScreen(this);
		gameScreen = new GameScreen();
		setScreen(mainMenuScreen);
	}

	@Override
	public void dispose() {
		mainMenuScreen.dispose();
		if(loadedGameScreen) {
			gameScreen.dispose();
		}
	}

}
