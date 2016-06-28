package com.jump.game.controllers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.jump.game.states.ChoosePlayerState;
import com.jump.game.states.MenuState;
import com.jump.game.states.PauseState;
import com.jump.game.states.State;

import java.util.HashMap;

/**
 * Created by jc on 18.06.16.
 */
public class ChoosePlayerController implements InputProcessor {

    private HashMap<Integer, Rectangle> players;
    private ChoosePlayerState state;

    public ChoosePlayerController(HashMap<Integer, Rectangle> players, ChoosePlayerState state) {
        this.players = players;
        this.state = state;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        if(!(state.getsManager().getState() instanceof PauseState)) {
            Vector2 valera = new Vector2(screenX, screenY);

            for (int i = 1; i <= players.size(); i++) {
                if (players.get(i).contains(valera))
                    state.createWorld(i);
            }
        }

        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.BACK || keycode == Input.Keys.HOME || keycode == Input.Keys.MENU) {
            state.getsManager().init(new MenuState(state.getsManager()));
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
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
