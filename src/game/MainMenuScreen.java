package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class MainMenuScreen implements Screen, InputProcessor {
	
	private MyGame game;
	private BitmapFont font;
	private SpriteBatch batch;
	private Rectangle startButton;
	private Rectangle optionsButton;
	private ShapeRenderer shapeRenderer;
	private Vector3 mousePos;
	private OrthographicCamera camera;
	private boolean debugging = true;
	
	public MainMenuScreen(MyGame game) {
		this.game = game;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		batch.dispose();
		shapeRenderer.dispose();
		font.dispose();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

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
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		
		batch.begin();
		font.draw(batch, "Start", startButton.x, startButton.y + startButton.height);
		font.draw(batch, "Options", optionsButton.x, optionsButton.y + optionsButton.height);
		batch.end();
		
		if(debugging) {
			shapeRenderer.begin(ShapeType.Rectangle);
			shapeRenderer.setColor(Color.RED);
			shapeRenderer.rect(startButton.x, startButton.y, startButton.width, startButton.height);
			
			shapeRenderer.rect(optionsButton.x, optionsButton.y, optionsButton.width, optionsButton.height);
			shapeRenderer.end();
		}
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
		BitmapFont.TextBounds startBounds = font.getBounds("Start");
		BitmapFont.TextBounds optionBounds = font.getBounds("Options");
		startButton = new Rectangle(300.0f, 650.0f, startBounds.width, startBounds.height);
		optionsButton = new Rectangle(300.0f, 600.0f, optionBounds.width, optionBounds.height);
		Gdx.input.setInputProcessor(this);
		shapeRenderer = new ShapeRenderer();
		mousePos = new Vector3();
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		camera.unproject(mousePos.set(x, y, 0));
		if(startButton.contains(mousePos.x, mousePos.y)) {
			game.setScreen(game.gameScreen);
		}
		else if(optionsButton.contains(mousePos.x, mousePos.y)) {
			game.setScreen(game.optionsScreen);
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
