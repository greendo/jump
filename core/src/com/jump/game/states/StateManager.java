package com.jump.game.states;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by jc on 23.05.16.
 */
public class StateManager {

    private State state;

    public StateManager() {state = new MenuState(this);}

    public void init(State state) {this.state = state;}

    /** draw screen */
    public void render(SpriteBatch sb, BitmapFont font) {state.render(sb, font);}
    /** update screen */
    public void update(float delta) {state.update(delta);}
    public State getState() {return state;}
}
