package com.jump.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by jc on 23.05.16.
 */
public class PauseState extends State {

    private GameState game;

    public PauseState(StateManager sm, GameState game) {
        super(sm);
        this.game = game;
    }

    private GameState continueGame() {return game;}

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched() || Gdx.input.isKeyPressed(Input.Keys.ANY_KEY))
            continueGame();
    }

    @Override
    public void update(float delta) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb, BitmapFont font) {

    }

    @Override
    public void dispose() {

    }
}
