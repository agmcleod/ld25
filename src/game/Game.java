package game;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
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
	// private Vector3 focusedVector;
	private BitmapFont font;
	private Array<Sprite> enemies;
	private int focusedEnemy = 0;
	private Vector2 targetPos;
	private Music music;
	private ShapeRenderer shapeRenderer;

	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		// get resources
		grassImage = new Texture(Gdx.files.internal("assets/grass.jpeg"));
		characterImage = new Texture(Gdx.files.internal("assets/characters.png"));
		font = new BitmapFont(Gdx.files.internal("assets/font.fnt"), Gdx.files.internal("assets/font.png"), false);
		music = Gdx.audio.newMusic(Gdx.files.internal("assets/My Song 2.mp3"));
		// setup song to start playing
		music.setLooping(true);
		music.setVolume(0.4f);
		music.play();
		
		// setup the sprite batch
		batch = new SpriteBatch();
		// the shape renderer for health bars
		shapeRenderer = new ShapeRenderer();
		
		// setup the grass tiles to render the background
		grassTiles = new Array<Tile>();
		grassRegion = new TextureRegion[4];
		grassRegion[0] = new TextureRegion(grassImage, 0f, 0f, 0.5f, 0.5f);
		grassRegion[1] = new TextureRegion(grassImage, 0.5f, 0f, 1f, 0.5f);
		grassRegion[2] = new TextureRegion(grassImage, 0f, 0.5f, 0.5f, 1f);
		grassRegion[3] = new TextureRegion(grassImage, 0.5f, 0.5f, 1f, 1f);
		initGrass();
		initHero();
		// input processor setup
		Gdx.input.setInputProcessor(this);
		// setup the enemies
		enemies = new Array<Sprite>();
		Sprite enemy = new Bat(5 * 32, 5 * 32, characterImage);
		enemies.add(enemy);
		targetPos = new Vector2(enemy.getX(), enemy.getY());
	}

	@Override
	public void dispose() {
		grassImage.dispose();
		characterImage.dispose();
		font.dispose();
		music.dispose();
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
		//camera.unproject(focusedVector.set(x, y, 0f));
		//turnCharacterToFaceCoords((int) focusedVector.x, (int) focusedVector.y);
		
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
		
		// change image for the hero depending on sprite position
		Sprite enemy = enemies.get(focusedEnemy);
		turnCharacterToFaceCoords(enemy.getX(), enemy.getY());
		
		// check if focused enemy needs to move
		enemy.moveTo((int) targetPos.x, (int) targetPos.y);
		hero.drawHealthBar(shapeRenderer);
		// check if enemy collides with the hero
		if(Intersector.intersectRectangles(hero.getCollisionRectangle(), enemy.getCollisionRectangle())) {
			if(!enemy.isColliding && !hero.isColliding) {
				enemy.health--;
				hero.health--;
			}
			enemy.isColliding = true;
			hero.isColliding = true;
		}
		else if(enemy.isColliding || hero.isColliding) {
			enemy.isColliding = false;
			hero.isColliding = false;
		}
		
		// drawing logic
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		tileBackground();
		stateTime += Gdx.graphics.getDeltaTime();
		hero.render(stateTime, batch);
		Iterator<Sprite> it = enemies.iterator();
		int removed = 0;
		// go through the spawned enemies, and check if a dead one needs to be removed
		while(it.hasNext()) {
			Sprite e = it.next();
			if(e.health <= 0) {
				it.remove();
				removed++;
			}
			else {
				e.render(stateTime, batch);
			}
		}
		// add a new enemy to replace the lost ones
		for(int i = 0; i < removed; i++) {
			enemies.add(new Bat(MathUtils.random(1, 20) * 32, MathUtils.random(1, 20) * 32, characterImage));
		}
		// reset the target position
		if(removed > 0) {
			focusedEnemy = 0;
			this.targetPos = new Vector2(enemies.get(0).getX(), enemies.get(0).getY());
		}
		
		// end game if hero dies
		if(hero.health <= 0) {
			Gdx.app.exit();
		}
		
		// font.draw(batch, "x: " + targetPos.x + " y: " + targetPos.y, 10, 50);
		
		batch.end();
		hero.drawHealthBar(shapeRenderer);
		it = enemies.iterator();
		while(it.hasNext()) {
			Sprite e = it.next();
			e.drawHealthBar(shapeRenderer);
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
	
	public void tileBackground() {
		Iterator<Tile> it = this.grassTiles.iterator();
		while(it.hasNext()) {
			Tile t = it.next();
			batch.draw(grassRegion[t.getTexture()], t.x, t.y);
		}
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		Vector3 position = new Vector3();
		camera.unproject(position.set(x, y, 0f));
		targetPos.x = position.x;
		targetPos.y = position.y;
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
	
	public void turnCharacterToFaceCoords(int x, int y) {
		float angle = MathUtils.atan2(y - hero.getY(), x - hero.getX());
		angle = angle * (180/MathUtils.PI);
		if(angle < 0)
		{
		    angle = 360 - (-angle);
		}
		int idx = ((int) angle / 90);
		if(idx < 0) {
			idx = 0;
		}
		hero.setFocusedAnimation(idx);
	}

}
