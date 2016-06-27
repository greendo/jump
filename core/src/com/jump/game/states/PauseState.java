package com.jump.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jump.game.Jumper;

/**
 * Created by jc on 23.05.16.
 */
public class PauseState extends State {

    private State state;

    public PauseState(StateManager sm, State state) {
        super(sm);
        this.state = state;
    }

    @Override
    public void continueGame() {sManager.init(state);}

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched() || Gdx.input.isKeyPressed(Input.Keys.ANY_KEY))
            continueGame();
    }

    @Override
    public void update(float delta) {handleInput();}

    @Override
    public void render(SpriteBatch sb, BitmapFont font) {
        //sb.setProjectionMatrix(camera.combined);
        sb.begin();
        font.draw(sb, "pause, tap to continue", Jumper.WIDTH / 2 - 10, Jumper.HEIGHT / 2);
        sb.end();
    }

    @Override
    public void dispose() {}
}
