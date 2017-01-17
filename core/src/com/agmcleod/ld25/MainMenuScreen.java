package com.agmcleod.ld25;

import java.awt.event.KeyEvent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class MainMenuScreen implements Screen, InputProcessor {

    private MyGame game;
    private BitmapFont font;
    private GlyphLayout layout;
    private SpriteBatch batch;
    private Rectangle startButton;
    private Rectangle optionsButton;
    private ShapeRenderer shapeRenderer;
    private Vector3 mousePos;
    private OrthographicCamera camera;
    private boolean debugging = false;

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
    public boolean keyDown(int keyCode) {
        System.out.println(KeyEvent.getKeyText(keyCode));
        return false;
    }

    @Override
    public boolean keyTyped(char arg0) {
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
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        batch.begin();
        font.draw(batch, "Start", startButton.x, startButton.y + startButton.height);
        font.draw(batch, "Options", optionsButton.x, optionsButton.y + optionsButton.height);
        batch.end();

        if(debugging) {
            shapeRenderer.begin(ShapeType.Filled);
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
        font = new BitmapFont(Gdx.files.internal("font.fnt"), Gdx.files.internal("font.png"), false);
        layout = new GlyphLayout();
        batch = new SpriteBatch();
        layout.setText(font, "Start");
        startButton = new Rectangle(300.0f, 650.0f, layout.width, layout.height);
        layout.setText(font, "Options");
        optionsButton = new Rectangle(300.0f, 600.0f, layout.width, layout.height);
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

