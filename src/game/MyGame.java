package game;
import com.badlogic.gdx.Game;


public class MyGame extends Game {
	
	GameScreen gameScreen;
	MainMenuScreen mainMenuScreen;
	EndGameScreen endGameScreen;
	boolean loadedGameScreen = false;

	@Override
	public void create() {
		mainMenuScreen = new MainMenuScreen(this);
		gameScreen = new GameScreen(this);
		endGameScreen = new EndGameScreen();
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
