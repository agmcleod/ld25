package game;

import java.util.Iterator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen, InputProcessor {
	
	private OrthographicCamera camera;
	private Texture grassImage;
	private Texture characterImage;
	private SpriteBatch batch;
	private Array<Tile> grassTiles;
	private TextureRegion[] grassRegion;
	private Hero hero;
	private float stateTime = 0f;
	// private Vector3 focusedVector;
	private BitmapFont font;
	private Array<Sprite> enemies;
	private int focusedEnemy = 0;
	private Vector2 currentPos;
	private Music music;
	private ShapeRenderer shapeRenderer;
	
	public GameScreen() {
		
	}
	
	public void checkCollisions() {
		// check if enemy collides with the hero
		Iterator<Sprite> it = enemies.iterator();
		while(it.hasNext()) {
			Sprite enemy = it.next();
			if(Intersector.intersectRectangles(hero.getCollisionRectangle(), enemy.getCollisionRectangle())) {
			  if(!enemy.isColliding && !hero.isColliding) {
			    float angle = MathUtils.atan2(enemy.getY() - hero.getY(), enemy.getX() - hero.getX());
			    angle = angle * (180/MathUtils.PI);
			    if(angle < 0) {
			        angle = 360 - (-angle);
			    }
			    if((int) angle / 90 == hero.getFacingDirection()) {
			      enemy.health--;
			    }
			    else {
			      hero.health--;
			    }
			  }
			  enemy.isColliding = true;
			  hero.isColliding = true;
			}
			else if(enemy.isColliding || hero.isColliding) {
			  enemy.isColliding = false;
			  hero.isColliding = false;
			}
		}
	}

	@Override
	public void dispose() {
		grassImage.dispose();
		characterImage.dispose();
		font.dispose();
		music.dispose();
	}
	
	public Sprite getEnemyAtCoords(int x, int y) {
		return this.enemies.get(getEnemyIndexAtCoords(x, y));
	}
	
	public int getEnemyIndexAtCoords(int x, int y) {
		int idx = -1;
		for(int i = 0; i < this.enemies.size; i++) {
			Sprite e = this.enemies.get(i);
			Rectangle r = new Rectangle(e.getX(), e.getY(), e.getWidth(), e.getHeight());
			if(r.contains(x, y)) {
				idx = i;
				break;
			}
		}
		return idx;
	}
	
	@Override
	public void hide() {
		// TODO Auto-generated method stub

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
		hero = new game.Hero(16 * 32, 12 * 32, 32, 32, characterImage, false);
		hero.addFrame(32, 0, 32, 32);
		hero.addFrame(32, 32, 32, 32);
		hero.addFrame(0, 32, 32, 32);
		hero.addFrame(0, 0, 32, 32);
	}

	@Override
	public boolean keyDown(int key) {
		// if user presses space, spawn a bat
		if(key == Input.Keys.SPACE && this.enemies.size < 5) {
			enemies.add(spawnEnemy());
		}
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
		
		// change image for the hero depending on sprite position
		Sprite enemy = enemies.get(focusedEnemy);
		
		// check if enemy needs to move
		Iterator<Sprite> it = enemies.iterator();
		while(it.hasNext()) {
			Sprite e = it.next();
			if(e.moveTo((int) e.getTargetPos().x, (int) e.getTargetPos().y)) {
				currentPos.x = e.getX();
				currentPos.y = e.getY();
			}
		}
		
		hero.drawHealthBar(shapeRenderer);
		
		checkCollisions();
		
		// drawing logic
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		tileBackground();
		stateTime += Gdx.graphics.getDeltaTime();
		hero.render(stateTime, batch);
		it = enemies.iterator();
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
		// reset the target
		if(removed > 0) {
			focusedEnemy = 0;
			if(this.enemies.size == 0) {
				this.enemies.add(spawnEnemy());
			}
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
			e.drawBox(shapeRenderer);
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
	
	@Override
	public void show() {
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
		enemy.setFocused(true);
		enemies.add(enemy);
		currentPos = new Vector2(enemy.getX(), enemy.getY());
	}
	
	public Sprite spawnEnemy() {
		return new Bat(MathUtils.random(1, 20) * 32, MathUtils.random(1, 20) * 32, characterImage);
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
		int enemyIndex = getEnemyIndexAtCoords((int) position.x, (int) position.y);
		
		if(enemyIndex > -1) {
			this.enemies.get(focusedEnemy).setFocused(false);
			focusedEnemy = enemyIndex;
			Sprite enemy = this.enemies.get(focusedEnemy);
			enemy.setFocused(true);
			turnCharacterToFaceCoords((int) enemy.getTargetPos().x, (int) enemy.getTargetPos().y);
		}
		else {
			Sprite enemy = this.enemies.get(focusedEnemy);
			enemy.setTargetPos(new Vector2(position.x, position.y));
			enemy.setFocused(true);
			turnCharacterToFaceCoords((int) enemy.getTargetPos().x, (int) enemy.getTargetPos().y);
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
	
	public void turnCharacterToFaceCoords(int x, int y) {
		float angle = MathUtils.atan2(y - hero.getY(), x - hero.getX());
		angle = angle * (180/MathUtils.PI);
		if(angle < 0) {
		    angle = 360 - (-angle);
		}
		int idx = ((int) angle / 90);
		if(idx < 0) {
			idx = 0;
		}
		hero.setFocusedAnimation(idx);
		// divide and multiple by 90 to get to specific degree values
		hero.setFacingDirection((int) angle / 90);
	}

}
