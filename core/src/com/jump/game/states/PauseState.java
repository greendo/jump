package com.jump.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jump.game.Jumper;
import com.jump.game.controllers.ActionController;
import com.jump.game.controllers.PauseController;

/**
 * Created by jc on 23.05.16.
 */
public class PauseState extends State {

    private State state;

    public PauseState(StateManager sm, State state) {
        super(sm);
        this.state = state;
        camera.setToOrtho(false, Jumper.WIDTH, Jumper.HEIGHT);

        Gdx.input.setInputProcessor(new PauseController(this));
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);
    }

    @Override
    public void continueGame() {
        sManager.init(state);
        Gdx.input.setInputProcessor(new ActionController(state.getPlayer(), state));
    }

    @Override
    protected void handleInput() {}

    @Override
    public void update(float delta) {handleInput();}

    @Override
    public void render(SpriteBatch sb, BitmapFont font) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        font.draw(sb, "pause\nTAP to continue\nBACK to main menu", Jumper.WIDTH / 2, Jumper.HEIGHT / 2);
        sb.end();
    }

    @Override
    public void dispose() {}
}
