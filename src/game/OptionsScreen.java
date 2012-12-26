package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entries;

public class OptionsScreen implements Screen, InputProcessor {
	private MyGame game;
	private ShapeRenderer shapeRenderer;
	private BitmapFont font;
	private SpriteBatch batch;
	private Rectangle backButton;
	// action name with a binding, which stores the location of the binding on the interface, so it can be clicked
	private ObjectMap<String, Rectangle> bindingButtons;
	private Vector3 mousePos;
	private OrthographicCamera camera;
	private boolean focused = false;
	private Rectangle focusedBox;
	private String focusedAction = "";
	
	public OptionsScreen(MyGame game) {
		this.game = game;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		font.dispose();
		shapeRenderer.dispose();
		batch.dispose();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
	}
	
	public void goBack() {
		game.setScreen(game.mainMenuScreen);
	}

	@Override
	public boolean keyDown(int keyCode) {
		updateBinding(keyCode);
		return false;
	}

	@Override
	public boolean keyTyped(char key) {
		return false;
	}

	@Override
	public boolean keyUp(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float arg0) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			if(focused) {
				focused = false;
			}
			else {
				goBack();
			}
		}
		
		if(focused) {
			shapeRenderer.begin(ShapeType.FilledRectangle);
			Color c = new Color(0.15f, 0.4f, 0.15f, 1f);
			shapeRenderer.setColor(c);
			shapeRenderer.filledRect(focusedBox.x, focusedBox.y, focusedBox.width, focusedBox.height);
			shapeRenderer.end();
		}
		
		batch.begin();
		ObjectMap.Entries<String, Rectangle> it = bindingButtons.entries();
		// draw back button text
		font.draw(batch, "Back", backButton.x, backButton.y+backButton.height);
		// draw option texts
		while(it.hasNext()) {
			ObjectMap.Entry<String, Rectangle> entry = it.next();
			Rectangle bounds = entry.value;
			font.draw(batch, entry.key, bounds.x, bounds.y+bounds.height);
			Binding binding = game.getBindings().get(entry.key);
			font.draw(batch, binding.character, bounds.x + 200.0f, bounds.y+bounds.height);
		}
		batch.end();
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean scrolled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void show() {
		font = new BitmapFont(Gdx.files.internal("assets/font.fnt"), Gdx.files.internal("assets/font.png"), false);
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		bindingButtons = new ObjectMap<String, Rectangle>();
		backButton = new Rectangle(40.0f, 730.0f, 100.0f, 32.0f);
		
		// setup interface
		ObjectMap.Entries<String, Binding> it = game.getBindings().entries();
		float y = 700.0f;
		while(it.hasNext()) {
			ObjectMap.Entry<String, Binding> entry = it.next();
			Rectangle button = new Rectangle(300.0f, y, 150.0f, 32);
			y -= 40;
			bindingButtons.put(entry.key, button);
		}
		mousePos = new Vector3();
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		Gdx.input.setInputProcessor(this);
		focusedBox = new Rectangle();
	}

	@Override
	public boolean touchDown(int x, int y, int arg2, int arg3) {
		camera.unproject(mousePos.set(x, y, 0));
		if(backButton.contains(mousePos.x, mousePos.y)) {
			goBack();
		}
		ObjectMap.Entries<String, Rectangle> entries = bindingButtons.entries();
		boolean focusedAnOption = false;
		while(entries.hasNext()) {
			ObjectMap.Entry<String, Rectangle> entry = entries.next();
			Rectangle bounds = entry.value;
			camera.unproject(mousePos.set(x, y, 0));
			if(bounds.contains(mousePos.x, mousePos.y)) {
				this.focused = true;
				focusedAnOption = true;
				this.focusedBox.setY(bounds.y);
				this.focusedBox.setX(bounds.x);
				BitmapFont.TextBounds b = font.getBounds(entry.key);
				this.focusedBox.setHeight(b.height);
				this.focusedBox.setWidth(b.width);
				this.focusedAction = entry.key;
			}
		}
		if(!focusedAnOption) {
			this.focused = false;
		}
		return false;
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void updateBinding(int keycode) {
		ObjectMap<String, Binding> bindings = game.getBindings();
		Binding binding = bindings.get(this.focusedAction);
		String character = game.map.get(keycode);
		if(character != null) {
			binding.character = character; 
			binding.keyCode = keycode;
			System.out.println("Updating action: " + this.focusedAction + " to " + keycode + " " + binding.character);
			bindings.put(focusedAction, binding);
		} else {
			System.out.println("Couldnt map character: " + keycode);
		}
	}

}
