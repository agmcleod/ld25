package game;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Vector3;
import java.util.Iterator;


public class Game implements ApplicationListener, InputProcessor {
	
	private OrthographicCamera camera;
	private Texture grassImage;
	private Texture characterImage;
	private SpriteBatch batch;
	private Array<Tile> grassTiles;
	private TextureRegion[] grassRegion;
	private Sprite hero;
	private float stateTime = 0f;
	private Vector3 focusedVector;
	private BitmapFont font;
	private float mouseAngle;

	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		grassImage = new Texture(Gdx.files.internal("assets/grass.jpeg"));
		characterImage = new Texture(Gdx.files.internal("assets/characters.png"));
		batch = new SpriteBatch();
		grassTiles = new Array<Tile>();
		grassRegion = new TextureRegion[4];
		grassRegion[0] = new TextureRegion(grassImage, 0f, 0f, 0.5f, 0.5f);
		grassRegion[1] = new TextureRegion(grassImage, 0.5f, 0f, 1f, 0.5f);
		grassRegion[2] = new TextureRegion(grassImage, 0f, 0.5f, 0.5f, 1f);
		grassRegion[3] = new TextureRegion(grassImage, 0.5f, 0.5f, 1f, 1f);
		initGrass();
		initHero();
		focusedVector = new Vector3();
		font = new BitmapFont(Gdx.files.internal("assets/font.fnt"), Gdx.files.internal("assets/font.png"), false);
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void dispose() {
		grassImage.dispose();
		characterImage.dispose();
		font.dispose();
	}
	
	public void initGrass() {
		// loop through height first so it goes on one row, then the next and so forth
		for(int y = 0; y < Gdx.graphics.getHeight() / 32; y++) {
			for(int x = 0; x < Gdx.graphics.getWidth() / 32; x++) {
				Tile tile = new Tile();
				tile.x = x * 32;
				tile.y = y * 32;
				tile.width = 32;
				tile.height = 32;
				tile.setTexture(MathUtils.random(0, 3));
				this.grassTiles.add(tile);
			}
		}
	}
	
	public void initHero() {
		hero = new game.Sprite(16 * 32, 12 * 32, 32, 32, characterImage, false);
		hero.addFrame(32, 0, 32, 32);
		hero.addFrame(32, 32, 32, 32);
		hero.addFrame(0, 32, 32, 32);
		hero.addFrame(0, 0, 32, 32);
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
	public boolean mouseMoved(int x, int y) {
		camera.unproject(focusedVector.set(x, y, 0f));
		float angle = MathUtils.atan2(focusedVector.y - hero.getY(), focusedVector.x - hero.getX());
		angle = angle * (180/MathUtils.PI);
		if(angle < 0)
		{
		    angle = 360 - (-angle);
		}
		this.mouseAngle = angle;
		int idx = ((int) angle / 90); // - 1;
		if(idx < 0) {
			idx = 0;
		}
		hero.setFocusedAnimation(idx);
		
		return false;
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		tileBackground();
		stateTime += Gdx.graphics.getDeltaTime();
		hero.render(stateTime, batch);
		font.draw(batch, String.valueOf(this.mouseAngle), 40, 40);
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
	
	public void tileBackground() {
		Iterator<Tile> it = this.grassTiles.iterator();
		while(it.hasNext()) {
			Tile t = it.next();
			batch.draw(grassRegion[t.getTexture()], t.x, t.y);
		}
	}

	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
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
