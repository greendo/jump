package com.jump.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by jc on 23.05.16.
 */
public abstract class State {

    protected OrthographicCamera camera;
    protected Vector2 vector;
    protected StateManager sManager;

    public State(StateManager sm) {
        camera = new OrthographicCamera();
        vector = new Vector2();
        this.sManager = sm;
    }

    /** controls */
    protected abstract void handleInput();
    /** update screen */
    public abstract void update(float delta);
    /** draw */
    public abstract void render(SpriteBatch sb, BitmapFont font);
    /** free mmr */
    public abstract void dispose();
    /** for pause */
    public abstract void continueGame();

    public StateManager getsManager() {return sManager;}
}
