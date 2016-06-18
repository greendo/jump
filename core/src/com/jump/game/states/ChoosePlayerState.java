package com.jump.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jump.game.ChoosePlayerController;

/**
 * Created by jc on 18.06.16.
 */
public class ChoosePlayerState extends State {

    public ChoosePlayerState(StateManager sm) {
        super(sm);
        /** for swipes */

        Gdx.input.setInputProcessor(new ChoosePlayerController());
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(SpriteBatch sb, BitmapFont font) {

    }

    @Override
    public void dispose() {

    }
}
