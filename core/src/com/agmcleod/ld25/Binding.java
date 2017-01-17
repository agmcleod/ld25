package com.agmcleod.ld25;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Binding {
    private Rectangle bounds;
    // store the action key, as well as the keycode
    String action;
    String character;
    int keyCode;

    public Binding(int keyCode, String character) {
        this.keyCode = keyCode;
        this.character = character;
    }

    public void drawBounds(ShapeRenderer sr) {
        sr.rect(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }
}
