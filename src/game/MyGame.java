package game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.ObjectMap;


public class MyGame extends Game {
	
	GameScreen gameScreen;
	MainMenuScreen mainMenuScreen;
	EndGameScreen endGameScreen;
	OptionsScreen optionsScreen;
	ObjectMap<Integer, String> map;
	boolean loadedGameScreen = false;
	private ObjectMap<String, Binding> bindings;

	public void changeBinding(String key, int value) {
		Binding temp = bindings.get(key);
		temp.keyCode = value;
		bindings.put(key, temp);
	}
	
	@Override
	public void create() {
		bindings = new ObjectMap<String, Binding>();
		Binding spaceBinding = new Binding(Input.Keys.SPACE, "SpaceBar");
		Binding tabBinding = new Binding(Input.Keys.TAB, "Tab");
		bindings.put("spawn", spaceBinding);
		bindings.put("change enemy", tabBinding);
		mapKeys();
		
		// setup the screens and load the main menu
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
	
	public ObjectMap<String, Binding> getBindings() {
		return this.bindings;
	}
	
	public void mapKeys() {
		map = new ObjectMap<Integer, String>();
		map.put(Input.Keys.TAB, "Tab");
		map.put(Input.Keys.SPACE, "SpaceBar");
		map.put(Input.Keys.A, "A");
		map.put(Input.Keys.B, "B");
		map.put(Input.Keys.C, "C");
		map.put(Input.Keys.D, "D");
		map.put(Input.Keys.E, "E");
		map.put(Input.Keys.F, "F");
		map.put(Input.Keys.G, "G");
		map.put(Input.Keys.H, "H");
		map.put(Input.Keys.I, "I");
		map.put(Input.Keys.J, "J");
		map.put(Input.Keys.K, "K");
		map.put(Input.Keys.L, "L");
		map.put(Input.Keys.M, "M");
		map.put(Input.Keys.N, "N");
		map.put(Input.Keys.O, "O");
		map.put(Input.Keys.P, "P");
		map.put(Input.Keys.Q, "Q");
		map.put(Input.Keys.R, "R");
		map.put(Input.Keys.S, "S");
		map.put(Input.Keys.T, "T");
		map.put(Input.Keys.U, "U");
		map.put(Input.Keys.V, "V");
		map.put(Input.Keys.W, "W");
		map.put(Input.Keys.X, "X");
		map.put(Input.Keys.Y, "Y");
		map.put(Input.Keys.Z, "Z");
		map.put(Input.Keys.NUM_0, "0");
		map.put(Input.Keys.NUM_1, "1");
		map.put(Input.Keys.NUM_2, "2");
		map.put(Input.Keys.NUM_3, "3");
		map.put(Input.Keys.NUM_4, "4");
		map.put(Input.Keys.NUM_5, "5");
		map.put(Input.Keys.NUM_6, "6");
		map.put(Input.Keys.NUM_7, "7");
		map.put(Input.Keys.NUM_8, "8");
		map.put(Input.Keys.NUM_9, "9");
		System.out.println("V key: " + Input.Keys.V);
	}
}
