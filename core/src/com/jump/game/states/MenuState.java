package com.jump.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jump.game.Jumper;

/**
 * Created by jc on 22.05.16.
 */
public class MenuState extends State {

    private String tmp;

    public MenuState(StateManager sm) {
        super(sm);
        tmp = "Tap to start";
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched() || Gdx.input.isKeyPressed(Input.Keys.ANY_KEY))
            sManager.init(new ChoosePlayerState(sManager));
    }

    @Override
    public void update(float delta) {handleInput();}

    @Override
    public void render(SpriteBatch sb, BitmapFont font) {
        sb.begin();
        font.draw(sb, tmp, Jumper.WIDTH / 2 - 10, Jumper.HEIGHT / 2);
        sb.end();
    }

    @Override
    public void dispose() {}
}
