package com.agmcleod.ld25;

import com.badlogic.gdx.math.Rectangle;

public class Tile extends Rectangle {
    private static final long serialVersionUID = 1L;
    private int texture;

    public Tile() {

    }

    public int getTexture() {
        return texture;
    }

    public void setTexture(int texture) {
        this.texture = texture;
    }
}

