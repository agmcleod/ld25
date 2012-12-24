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
	private ObjectMap<String, Binding> optionButtons;
	private Vector3 mousePos;
	private OrthographicCamera camera;
	private boolean focused = false;
	private Rectangle focusedBox;
	
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
	public boolean keyDown(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char arg0) {
		// TODO Auto-generated method stub
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
			shapeRenderer.setColor(Color.BLUE);
			shapeRenderer.filledRect(focusedBox.x, focusedBox.y, focusedBox.width, focusedBox.height);
			shapeRenderer.end();
		}
		
		batch.begin();
		ObjectMap.Entries<String, Binding> it = optionButtons.entries();
		font.draw(batch, "Back", backButton.x, backButton.y+backButton.height);
		while(it.hasNext()) {
			ObjectMap.Entry<String, Binding> entry = it.next();
			Rectangle bounds = entry.value.getBounds();
			font.draw(batch, entry.key, bounds.x, bounds.y+bounds.height);
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
		optionButtons = new ObjectMap<String, Binding>();
		backButton = new Rectangle(40.0f, 730.0f, 100.0f, 32.0f);
		
		// setup interface
		ObjectMap.Entries<String, Integer> it = game.getBindings().entries();
		float y = 700.0f;
		while(it.hasNext()) {
			ObjectMap.Entry<String, Integer> entry = it.next();
			Rectangle button = new Rectangle(300.0f, y, 150.0f, 32);
			y -= 40;
			Binding b = new Binding(entry.key, entry.value);
			b.setBounds(button);
			optionButtons.put(entry.key, b);
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
		ObjectMap.Entries<String, Binding> entries = optionButtons.entries();
		boolean focusedAnOption = false;
		while(entries.hasNext()) {
			ObjectMap.Entry<String, Binding> entry = entries.next();
			Rectangle bounds = entry.value.getBounds();
			camera.unproject(mousePos.set(x, y, 0));
			if(bounds.contains(mousePos.x, mousePos.y)) {
				this.focused = true;
				focusedAnOption = true;
				this.focusedBox.setY(bounds.y);
				this.focusedBox.setX(bounds.x);
				BitmapFont.TextBounds b = font.getBounds(entry.key);
				this.focusedBox.setHeight(b.height);
				this.focusedBox.setWidth(b.width);
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

}
