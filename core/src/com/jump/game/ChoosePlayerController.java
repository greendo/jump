package com.jump.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by jc on 18.06.16.
 */
public class ChoosePlayerController implements InputProcessor {

    Rectangle r;

    @Override
    public boolean keyDown(int keycode) {
        //r.contains()
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
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
