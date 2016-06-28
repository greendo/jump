package com.jump.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.jump.game.sprites.Player;
import com.jump.game.states.PauseState;
import com.jump.game.states.State;

/**
 * Created by jc on 30.05.16.
 */
public class ActionController implements InputProcessor {

    private Vector2 positionStart, positionFin;
    private Player player;
    public static boolean touched = false;
    private State state;

    public ActionController(Player player, State state) {
        this.player = player;
        this.state = state;
        positionStart = new Vector2();
        positionFin = new Vector2();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if(!(state.getsManager().getState() instanceof PauseState)) {
            positionStart.x = screenX;
            positionStart.y = -1 * screenY;
            player.setCurrentTexture("down");
            touched = true;
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        if(!(state.getsManager().getState() instanceof PauseState)) {
            positionFin.set(screenX, -1 * screenY);

            positionFin.sub(positionStart);
            if (positionFin.x < 0)
                positionFin.x = 0;

            player.jump(positionFin);
            touched = false;
        }
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
//        if(keycode == Input.Keys.BACK || keycode == Input.Keys.HOME) {
//            gs.getsManager().init(new PauseState(gs.getsManager(), gs));
//        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.BACK || keycode == Input.Keys.HOME || keycode == Input.Keys.MENU) {
            state.getsManager().init(new PauseState(state.getsManager(), state));
            Gdx.app.error("KEY", ((Integer) keycode).toString());
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }


    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
