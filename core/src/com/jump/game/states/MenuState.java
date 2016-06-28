package com.jump.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jump.game.Jumper;
import com.jump.game.controllers.MenuController;

/**
 * Created by jc on 22.05.16.
 */
public class MenuState extends State {

    public MenuState(StateManager sm) {
        super(sm);
        camera.setToOrtho(false, Jumper.WIDTH, Jumper.HEIGHT);

        Gdx.input.setInputProcessor(new MenuController());
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched())
            sManager.init(new ChoosePlayerState(sManager));
        if(Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            Gdx.app.exit();
        }

    }

    @Override
    public void update(float delta) {handleInput();}

    @Override
    public void render(SpriteBatch sb, BitmapFont font) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        font.draw(sb, "Main menu\nTAP to select character\nBACK to exit", Jumper.WIDTH / 2 - 10, Jumper.HEIGHT / 2);
        //font.draw(sb, "Main menu, TAP to select character\nBACK to exit", camera.viewportWidth / 2 - 10, camera.viewportHeight / 2);
        sb.end();
    }

    @Override
    public void dispose() {
    }

    @Override
    public void continueGame() {}
}
