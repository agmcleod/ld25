package game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.ObjectMap;


public class MyGame extends Game {
	
	GameScreen gameScreen;
	MainMenuScreen mainMenuScreen;
	EndGameScreen endGameScreen;
	OptionsScreen optionsScreen;
	boolean loadedGameScreen = false;
	private ObjectMap<String, Integer> bindings;

	public void changeBinding(String key, int value) {
		this.bindings.put(key, value);
	}
	
	@Override
	public void create() {
		bindings = new ObjectMap<String, Integer>();
		bindings.put("spawn", Input.Keys.SPACE);
		bindings.put("change enemy", Input.Keys.TAB);
		mainMenuScreen = new MainMenuScreen(this);
		gameScreen = new GameScreen(this);
		endGameScreen = new EndGameScreen();
		optionsScreen = new OptionsScreen(this);
		setScreen(mainMenuScreen);
	}

	@Override
	public void dispose() {
		mainMenuScreen.dispose();
		if(loadedGameScreen) {
			gameScreen.dispose();
		}
	}
	
	public ObjectMap<String, Integer> getBindings() {
		return this.bindings;
	}

}
